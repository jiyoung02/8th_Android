package com.example.flo.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import androidx.room.Entity
import androidx.room.PrimaryKey

@Parcelize
@Entity(tableName = "AlbumTable")
data class Album(
    var title : String? = "",
    var singer : String? = "",
    var coverImg : Int? = null,


):  Parcelable{
    @PrimaryKey(autoGenerate = true) var id : Int = 0
}