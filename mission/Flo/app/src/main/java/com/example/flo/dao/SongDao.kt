package com.example.flo.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.flo.data.Song

@Dao
interface SongDao {
    @Insert
    fun insert(song : Song)
    @Update
    fun update(song: Song)
    @Delete
    fun delete(song: Song)

    @Query("SELECT * FROM SongTable")
    fun getSongs() : List<Song>

    @Query("SELECT * FROM SongTable where id = :id")
    fun getSong(id: Int) : Song

    @Query("select albumId from SongTable where id = :id")
    fun getAlbumId(id: Int): Int

    @Query("UPDATE SongTable SET isLike= :isLike WHERE id = :id")
    fun updateIsLikeById(isLike: Boolean,id: Int)

    @Query("SELECT * FROM SongTable WHERE isLike= :isLike")
    fun getLikedSongs(isLike: Boolean): List<Song>
}