package com.example.flo.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class Song(
    val title: String ="",
    val singer: String = "",
    var second : Int = 0,
    var playTime : Int = 0,
    var isPlaying : Boolean = false
) : Parcelable
