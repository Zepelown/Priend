package com.example.priend.main.censor.view

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.SeekBar
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.registerReceiver
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.priend.databinding.FragmentCensorBinding
import dagger.hilt.android.AndroidEntryPoint
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.util.UUID

@AndroidEntryPoint
class CensorFragment : Fragment() {

    private var _binding : FragmentCensorBinding? = null

    private val binding get() = _binding!!

    private val censorViewModel : CensorViewModel by viewModels()

    private val bluetoothAdapter: BluetoothAdapter? = BluetoothAdapter.getDefaultAdapter()
    private var bluetoothSocket: BluetoothSocket? = null
    private val uuid: UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB")

    private lateinit var listAdapter: ArrayAdapter<String>
    private val devicesList: MutableList<BluetoothDevice> = mutableListOf()

    private val handler = Handler()
    private lateinit var inputStream: InputStream
    private lateinit var outputStream: OutputStream
    private var buffer = ByteArray(1024)
    private var bufferPosition = 0

    private var deviceSelectionDialog: AlertDialog? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCensorBinding.inflate(layoutInflater)

        binding.lifecycleOwner = this
        binding.censorViewModel = censorViewModel

        listAdapter = ArrayAdapter(requireActivity(), android.R.layout.simple_list_item_1)

        if (ContextCompat.checkSelfPermission(requireActivity(), Manifest.permission.BLUETOOTH) != PackageManager.PERMISSION_GRANTED ||
            ContextCompat.checkSelfPermission(requireActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.BLUETOOTH, Manifest.permission.ACCESS_FINE_LOCATION), 1)
        }

        censorViewModel.soilMoistureCondition.observe(viewLifecycleOwner, Observer {

        })
        binding.connectButton.setOnClickListener {
            startDiscovery()
            showDeviceSelectionDialog()
        }

        binding.ageSeekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                binding.ageTextview.text = progress.toString()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                sendAgeStatus(seekBar!!.progress)
            }
        })

        val filter = IntentFilter(BluetoothDevice.ACTION_FOUND)
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED)
        registerReceiver(requireContext(),receiver, filter, ContextCompat.RECEIVER_EXPORTED)

        censorViewModel.airTemperatureCondition.observe(viewLifecycleOwner, Observer {
            binding.airTemperatureData.text = censorViewModel.getAirTemperature()
        })

        censorViewModel.soilMoistureCondition.observe(viewLifecycleOwner, Observer {
            val newData = censorViewModel.getSoilMoistureStatus()
            binding.soilMoistureData.text = newData.string
            binding.soilMoistureData.setTextColor(ContextCompat.getColor(requireContext(), newData.colorResId))
        })
        censorViewModel.airMoistureCondition.observe(viewLifecycleOwner, Observer {
            val newData = censorViewModel.getAirMoistureStatus()
            binding.airMoistureData.text = newData.string
            binding.airMoistureData.setTextColor(ContextCompat.getColor(requireContext(),newData.colorResId))
        })

        val spf = requireActivity().getSharedPreferences("PriendPrefs",
            AppCompatActivity.MODE_PRIVATE
        )

        binding.plantNameTextview.text = spf.getString("plantName", "null")

        binding.defaultSoilCensorThresholdButton.setOnClickListener {
            censorViewModel.setSoilMoistureStandard(600)
            binding.soilMoistureStandardData.text = "600"
            sendData("soil,default")
        }

        binding.lowSoilCensorThresholdButton.setOnClickListener {
            censorViewModel.setSoilMoistureStandard(400)
            binding.soilMoistureStandardData.text = "400"
            sendData("soil,low")
        }

        binding.highSoilCensorThresholdButton.setOnClickListener {
            censorViewModel.setSoilMoistureStandard(800)
            binding.soilMoistureStandardData.text = "800"
            sendData("soil,high")
        }

        binding.defaultAirCensorThresholdButton.setOnClickListener {
            censorViewModel.setAirMoistureStandard(40)
            binding.airMoistureStandardData.text = "40"
            sendData("air,default")
        }
        binding.lowAirCensorThresholdButton.setOnClickListener {
            censorViewModel.setAirMoistureStandard(20)
            binding.airMoistureStandardData.text = "20"
            sendData("air,low")
        }
        binding.highAirCensorThresholdButton.setOnClickListener {
            censorViewModel.setAirMoistureStandard(60)
            binding.airMoistureStandardData.text = "60"
            sendData("air,high")
        }

        return binding.root
    }

    private fun startDiscovery() {
        devicesList.clear()
        listAdapter.clear()
        if (ActivityCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.BLUETOOTH_SCAN
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        bluetoothAdapter?.startDiscovery()
    }

    private val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val action = intent?.action
            if (BluetoothDevice.ACTION_FOUND == action) {
                val device: BluetoothDevice? = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE)
                device?.let {
                    devicesList.add(it)
                    if (ActivityCompat.checkSelfPermission(
                            requireActivity(),
                            Manifest.permission.BLUETOOTH_CONNECT
                        ) != PackageManager.PERMISSION_GRANTED
                    ) {
                        return
                    }
                    listAdapter.add("${it.name} (${it.address})")
                    listAdapter.notifyDataSetChanged()
                }
            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED == action) {
                if (devicesList.isEmpty()) {
                    deviceSelectionDialog?.dismiss()
                    showNoDevicesFoundDialog()
                }
            }
        }
    }

    private fun showDeviceSelectionDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Select a device")

        val listView = ListView(requireContext())
        listView.adapter = listAdapter
        listView.setOnItemClickListener { _, _, which, _ ->
            val device = devicesList[which]
            connectToDevice(device)
            deviceSelectionDialog?.dismiss()
        }

        builder.setView(listView)
        builder.setNegativeButton("Cancel") { dialog, _ ->
            dialog.dismiss()
        }
        deviceSelectionDialog = builder.create()
        deviceSelectionDialog?.show()
    }

    private fun showNoDevicesFoundDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("No devices found")
        builder.setMessage("No Bluetooth devices were found. Please make sure your Bluetooth device is in pairing mode and try again.")
        builder.setPositiveButton("OK") { dialog, _ ->
            dialog.dismiss()
        }
        builder.create().show()
    }

    private fun connectToDevice(device: BluetoothDevice) {
        if (ActivityCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.BLUETOOTH_SCAN
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        bluetoothAdapter?.cancelDiscovery()
        try {
            bluetoothSocket = device.createRfcommSocketToServiceRecord(uuid)
            bluetoothSocket?.connect()
            val connectedDeviceInfo = "${device.name} (${device.address})"
            showConnectedDeviceInfoToast(connectedDeviceInfo)
            inputStream = bluetoothSocket!!.inputStream
            outputStream = bluetoothSocket!!.outputStream
            listenForData()
        } catch (e: IOException) {
            showConnectionFailedToast()
            e.printStackTrace()
        }
    }

    private fun showConnectedDeviceInfoToast(deviceInfo: String) {
        Toast.makeText(requireContext(), "Connected to: $deviceInfo", Toast.LENGTH_SHORT).show()
    }

    private fun showConnectionFailedToast() {
        requireActivity().runOnUiThread {
            Toast.makeText(requireContext(), "Failed to connect to the device", Toast.LENGTH_SHORT).show()
        }
    }

    private fun listenForData() {
        if (!checkBluetoothLinked()) {
            return
        }
        Thread {
            while (true) {
                try {
                    val bytesAvailable = inputStream.available()
                    if (bytesAvailable > 0) {
                        val packetBytes = ByteArray(bytesAvailable)
                        inputStream.read(packetBytes)
                        for (i in 0 until bytesAvailable) {
                            val b = packetBytes[i]
                            if (b == '\n'.toByte()) {
                                val data = String(buffer, 0, bufferPosition).split(",")
                                bufferPosition = 0
                                handler.post {
                                    binding.dataTextView.text = data.joinToString()
                                    censorViewModel.setSoilMoistureCondition(data[0].toInt())
                                    censorViewModel.setAirMoistureCondition(data[1].toFloat().toInt())
                                    censorViewModel.setAirTemperatureCondition(data[2].toFloat().toInt())

                                }
                            } else {
                                buffer[bufferPosition++] = b
                            }
                        }
                    }
                } catch (e: IOException) {
                    e.printStackTrace()
                    break
                }
            }
        }.start()
    }

    private fun sendData(data: String) {
        if (!checkBluetoothLinked()) {
            return
        }
        try {
            outputStream.write(data.toByteArray())
        } catch (e: IOException) {
            e.printStackTrace()
            requireActivity().runOnUiThread {
                Toast.makeText(requireContext(), "Failed to send data", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        requireActivity().unregisterReceiver(receiver)
        bluetoothSocket?.close()
    }

    private fun checkBluetoothLinked(): Boolean {
        return if (bluetoothSocket?.isConnected == true) {
            true
        } else {
            Toast.makeText(requireContext(), "Bluetooth is not connected", Toast.LENGTH_SHORT).show()
            false
        }
    }

    private fun sendAgeStatus(age : Int){
        if (!checkBluetoothLinked()){
            return
        }
        when (age){
            in 1..10 -> sendData("voice,1")
            in 11..20 -> sendData("voice,2")
            in 20..40 -> sendData("voice,3")
            else -> sendData("voice,4")
        }

    }
}