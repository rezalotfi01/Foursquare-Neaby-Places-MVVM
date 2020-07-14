package com.reza.mymvvm.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters


import com.reza.mymvvm.pages.placedetails.data.Detail
import com.reza.mymvvm.pages.placedetails.data.DetailDao
import com.reza.mymvvm.pages.placeslist.data.Place
import com.reza.mymvvm.pages.placeslist.data.PlacesDao

/**
 * The Room database for locations-mvvm app
 */
@Database(
    entities = [Place::class, Detail::class],
    version = 1, exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun placesDao(): PlacesDao
    abstract fun detailDao(): DetailDao

    companion object {

        // For Singleton instantiation
        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        // Create the database
        private fun buildDatabase(context: Context): AppDatabase =
            Room.databaseBuilder(context, AppDatabase::class.java, "locations-db")
                .build()
    }
}
