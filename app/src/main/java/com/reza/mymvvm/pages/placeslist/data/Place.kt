package com.reza.mymvvm.pages.placeslist.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.reza.mymvvm.data.Converters
import com.reza.mymvvm.pages.placedetails.data.Contact


// result generated from /json

@Entity(tableName = "Places")
data class Place(
    @PrimaryKey(autoGenerate = false)
    var id: String = "",
    @TypeConverters(Converters::class)
    var categories: List<Category> = listOf(),
    @TypeConverters(Converters::class)
    var contact: Contact = Contact(),
    @TypeConverters(Converters::class)
    var location: Location = Location(),
    var name: String = "",
    var rating: Double = 0.0,
    var ratingColor: String = "",
    var timeZone: String = "",
    var url: String = "",
    var verified: Boolean = false,
    var userLatLng: String = "",
    var totalResult: Int = 0)

data class Category(
    val icon: Icon = Icon(),
    val id: String = "",
    val name: String = "",
    val pluralName: String = "",
    val primary: Boolean = false,
    val shortName: String = ""
)

data class Icon(
    val prefix: String = "",
    val suffix: String = ""
)

data class Location(
    val address: String = "",
    val cc: String = "",
    val city: String = "",
    val country: String = "",
    val crossStreet: String = "",
    val lat: Double = 0.0,
    val lng: Double = 0.0,
    val state: String = "",
    val distance: Int = 0
)
data class Item(
    var venue: Place = Place()
)

data class Group(
    val items: List<Item> = listOf(),
    val name: String = "",
    val type: String = ""
)

data class PlacesExploreModel(
    val groups: List<Group> = listOf(),
    val headerFullLocation: String = "",
    val headerLocation: String = "",
    val headerLocationGranularity: String = "",
    val suggestedRadius: Int = 0,
    val totalResults: Int = 0
)




