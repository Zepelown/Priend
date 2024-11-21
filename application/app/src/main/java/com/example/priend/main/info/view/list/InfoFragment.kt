package com.example.priend.main.info.view.list

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
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import com.example.priend.common.view.RecyclerViewItemClickListener
import com.example.priend.databinding.FragmentInfoBinding
import com.example.priend.main.info.view.list.recyclerview.InfoViewAdapter
import dagger.hilt.android.AndroidEntryPoint
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.util.UUID


@AndroidEntryPoint
class InfoFragment : Fragment(), RecyclerViewItemClickListener {
    private var _binding: FragmentInfoBinding? = null
    private val binding get() = _binding!!
    private val infoViewModel: InfoViewModel by viewModels()

    private val bluetoothAdapter: BluetoothAdapter? = BluetoothAdapter.getDefaultAdapter()
    private var bluetoothSocket: BluetoothSocket? = null
    private val uuid: UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB")

    private lateinit var bluetoothListAdapter: ArrayAdapter<String>
    private val devicesList: MutableList<BluetoothDevice> = mutableListOf()

    private val handler = Handler()
    private lateinit var inputStream: InputStream
    private lateinit var outputStream: OutputStream
    private var buffer = ByteArray(1024)
    private var bufferPosition = 0

    private lateinit var infoAdapter : InfoViewAdapter

    private var deviceSelectionDialog: AlertDialog? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentInfoBinding.inflate(layoutInflater)

        infoAdapter = InfoViewAdapter(
            mutableListOf(),
            this
        )

        binding.plantInfoList.apply {
            this.adapter = infoAdapter
        }

        infoViewModel.result.observe(viewLifecycleOwner) { newItems ->
            infoAdapter.submitItems(newItems)
        }

        infoViewModel.getPotData(1.0)


        bluetoothListAdapter = ArrayAdapter(requireActivity(), android.R.layout.simple_list_item_1)

        if (ContextCompat.checkSelfPermission(requireActivity(), Manifest.permission.BLUETOOTH) != PackageManager.PERMISSION_GRANTED ||
            ContextCompat.checkSelfPermission(requireActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.BLUETOOTH, Manifest.permission.ACCESS_FINE_LOCATION), 1)
        }

        return binding.root
    }

    override fun onItemClick(position: Int) {
        if (infoAdapter.itemCount - 1 == position){
            val filter = IntentFilter(BluetoothDevice.ACTION_FOUND)
            filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED)
            ContextCompat.registerReceiver(
                requireContext(),
                receiver,
                filter,
                ContextCompat.RECEIVER_EXPORTED
            )
            startDiscovery()
            showDeviceSelectionDialog()
        }
    }
    private fun startDiscovery() {
        devicesList.clear()
        bluetoothListAdapter.clear()
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
                    bluetoothListAdapter.add("${it.name} (${it.address})")
                    bluetoothListAdapter.notifyDataSetChanged()
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
        listView.adapter = bluetoothListAdapter
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