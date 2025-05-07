package com.example.flo.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Album(
    var title : String? = "",
    var singer : String? = "",
    var coverImg : Int? = null,
    var songs : ArrayList<Song>? = null
): Parcelable