package com.reza.mymvvm.data

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.reza.mymvvm.pages.placedetails.data.BestPhoto
import com.reza.mymvvm.pages.placedetails.data.Contact
import com.reza.mymvvm.pages.placedetails.data.Photos
import com.reza.mymvvm.pages.placeslist.data.Category
import com.reza.mymvvm.pages.placeslist.data.Location

/**
 * Type converters to allow Room to reference complex data types.
 */
class Converters {

    @TypeConverter fun categoryObj2Str(category: List<Category>?): String? =
        if (category != null) Gson().toJson(category) else null

    @TypeConverter fun categoryStr2Obj(str: String?): List<Category>? =
        if (str != null) {
            val type = object : TypeToken<List<Category>?>(){
            }.type
            Gson().fromJson(str, type)
        }else mutableListOf()


    @TypeConverter fun contactObj2Str(contact: Contact?): String? =
        if (contact != null) Gson().toJson(contact) else null

    @TypeConverter fun contactStr2Obj(str: String?): Contact? =
        if (str != null) {
            val type = object : TypeToken<Contact?>(){
            }.type
            Gson().fromJson(str, type)
        }else null


    @TypeConverter fun locationObj2Str(location: Location?): String? =
        if (location != null) Gson().toJson(location) else null

    @TypeConverter fun locationStr2Obj(str: String?): Location? =
        if (str != null) {
            val type = object : TypeToken<Location?>(){
            }.type
            Gson().fromJson(str, type)
        }else null


    @TypeConverter fun bestPhotoObj2Str(photo: BestPhoto?): String? =
        if (photo != null) Gson().toJson(photo) else null

    @TypeConverter fun bestPhotoStr2Obj(str: String?): BestPhoto? =
        if (str != null) {
            val type = object : TypeToken<BestPhoto?>(){
            }.type
            Gson().fromJson(str, type)
        }else null

    @TypeConverter fun photosObj2Str(photo: Photos?): String? =
        if (photo != null) Gson().toJson(photo) else null

    @TypeConverter fun photosStr2Obj(str: String?): Photos? =
        if (str != null) {
            val type = object : TypeToken<Photos?>(){
            }.type
            Gson().fromJson(str, type)
        }else null


}