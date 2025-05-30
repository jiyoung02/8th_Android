package com.example.flo.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize


@Parcelize
@Entity(tableName = "SongTable",
        foreignKeys = [
            ForeignKey(
             entity = Album::class,
            parentColumns = ["id"],
            childColumns = ["albumId"],
            onDelete = ForeignKey.CASCADE
            )],
            indices = [Index("albumId")]
        )
data class Song(
    val title: String ="",
    val singer: String = "",
    var second : Int = 0,
    var playTime : Int = 60,
    var isPlaying : Boolean = false,
    var music : String = "",
    var coverImg : Int? = null,
    var isLike : Boolean = false,
    var albumId  : Int = 0
) : Parcelable{
    @PrimaryKey(autoGenerate = true) var id : Int = 0
}
