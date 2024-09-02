package com.example.priend.common

import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter

object TextWithColorBindingAdapter {
    @JvmStatic
    @BindingAdapter("textWithColor")
    fun setTextWithColor(view: TextView, textWithColor: TextWithColor?) {
        textWithColor?.let {
            view.text = it.string
            view.setTextColor(ContextCompat.getColor(view.context, it.colorResId))
        }
    }
}