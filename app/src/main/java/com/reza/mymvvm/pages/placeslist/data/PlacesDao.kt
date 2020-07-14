package com.reza.mymvvm.pages.placeslist.data

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PlacesDao {
    @Query("SELECT * FROM Places")
    fun getPagedPlaces(): DataSource.Factory<Int, Place>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(places: List<Place>)

}