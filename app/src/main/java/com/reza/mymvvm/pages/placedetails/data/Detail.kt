package com.reza.mymvvm.pages.placedetails.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.reza.mymvvm.data.Converters
import com.reza.mymvvm.pages.placeslist.data.Category
import com.reza.mymvvm.pages.placeslist.data.Location


@Entity(tableName = "Details")
data class Detail(
    @PrimaryKey(autoGenerate = false)
    var id: String = "",
    @TypeConverters(Converters::class)
    var categories: List<Category>? = listOf(),
    @TypeConverters(Converters::class)
    var contact: Contact = Contact(),
    @TypeConverters(Converters::class)
    var location: Location = Location(),
    var name: String = "",
    var rating: Double = 0.0,
    var ratingColor: String? = "",
    var verified: Boolean = false,
    @TypeConverters(Converters::class)
    var bestPhoto: BestPhoto?,
    @TypeConverters(Converters::class)
    var photos: Photos? = Photos()

)

data class Contact(
    val phone: String = "",
    val formattedPhone: String = ""
)

data class BestPhoto(
    val id: String,
    val prefix: String,
    val suffix: String,
    val width: Int,
    val height: Int,
    val visibility: String
)

data class Photos(
    val count: Int = 0,
    val groups: List<Group>? = listOf()
)

data class Group(
    val count: Int? = 0,
    val items: List<Items?>? = listOf(),
    val name: String? = "",
    val type: String? = ""
)


data class Items (
    val createdAt: Int? = 0,
    val height: Int? = 0,
    val id: String? = "",
    val prefix: String? = "",
    val source: Source? = Source(),
    val suffix: String? = "",
    val user: User? = User(),
    val visibility: String? = "",
    val width: Int? = 0
)

data class Source(
    val name: String? = "",
    val url: String? = ""
)

data class User(
    val firstName: String? = "",
    val id: String? = "",
    val lastName: String? = "",
    val photo: Photo? = Photo()
)

data class Photo(
    val prefix: String? = "",
    val suffix: String? = ""
)

data class PlaceDetailModel(val venue: Detail)


