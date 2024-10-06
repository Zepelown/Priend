package com.example.priend.common

import androidx.annotation.ColorRes

data class TextWithColor(
    val string: String,
    @ColorRes val colorResId: Int
)
