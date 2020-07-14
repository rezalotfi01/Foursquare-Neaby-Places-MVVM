package com.reza.mymvvm.pages.placedetails.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface DetailDao {

    @Query("SELECT * FROM Details WHERE id = :id")
    fun getDetail(id: String): LiveData<Detail>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDetail(detail: Detail)

}