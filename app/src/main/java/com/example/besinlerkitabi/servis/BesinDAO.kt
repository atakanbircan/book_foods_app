package com.example.besinlerkitabi.servis

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.besinlerkitabi.model.Besin
@Dao
interface BesinDAO {

    //data aCCESS oBJECT

    @Insert
    suspend fun insertAll(vararg besin:Besin):List<Long>

    @Query("SELECT* FROM besin")
    suspend fun getAllBesin(): List<Besin>

    @Query("SELECT * FROM besin WHERE uuid =:uuid")
    suspend fun getBesin(uuid:Int):Besin


    @Query("DELETE FROM besin")
    suspend fun deleteAllBesin()
}