package com.example.flo.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.flo.data.Album
import com.example.flo.data.Song


@Dao
interface AlbumDao {
    @Insert
    fun insert(album : Album)
    @Update
    fun update(album : Album)
    @Delete
    fun delete(album : Album)

    @Query("SELECT * FROM AlbumTable")
    fun getAlbums(): List<Album>

    @Query("SELECT * FROM AlbumTable WHERE id = :id")
    fun getAlbum(id: Int): Album

    @Query("SELECT * FROM SongTable WHERE albumId = :id")
    fun getSongsByAlbumId(id: Int): List<Song>
}