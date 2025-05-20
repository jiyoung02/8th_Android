package com.example.flo.DB

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.flo.dao.AlbumDao
import com.example.flo.dao.SongDao
import com.example.flo.data.Album
import com.example.flo.data.Like
import com.example.flo.data.Song

@Database(entities = [Song::class, Album::class, Like::class], version = 1, exportSchema = false)
abstract class SongDatabase : RoomDatabase(){
    abstract fun songDao(): SongDao
    abstract fun albumDao() : AlbumDao

    companion object{
        private var instance: SongDatabase? = null
        @Synchronized
        fun getIntance(context: Context) : SongDatabase?{
            if(instance == null){
                synchronized(SongDatabase::class){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        SongDatabase::class.java,
                        "song-database"
                    ).allowMainThreadQueries().build()
                }
            }
            return instance
        }
    }
}