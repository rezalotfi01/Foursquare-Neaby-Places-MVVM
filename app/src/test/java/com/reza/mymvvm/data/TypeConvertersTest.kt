package com.reza.mymvvm.data

import com.reza.mymvvm.pages.placedetails.data.Photos
import com.reza.mymvvm.pages.placeslist.data.Category
import com.reza.mymvvm.pages.placeslist.data.Icon
import com.reza.mymvvm.pages.placeslist.data.Location
import org.junit.Assert.assertEquals
import org.junit.Test

class TypeConvertersTest {

    private val locationStr = "{\"address\":\"24metri boulevared farhang " +
            "square\",\"cc\":\"IR\",\"city\":\"Tehran\",\"country\":\"Iran\",\"crossStreet\":\"saadatabad\",\"lat\":35.784788,\"lng\"" +
            ":51.384723,\"state\":\"Tehran\",\"distance\":273}"
    private val locationObject = Location(address = "24metri boulevared farhang square"
        ,crossStreet =  "saadatabad",cc = "IR",lat = 35.784788, lng =  51.384723,distance = 273,city = "Tehran", state = "Tehran", country = "Iran")

    @Test fun json2Location() {
        assertEquals(Converters().locationStr2Obj(locationStr),locationObject)
    }
    @Test fun location2Json(){
        assertEquals(Converters().locationObj2Str(locationObject), locationStr)
    }

    private val photosStr = "{\"count\":0,\"groups\":[]}"
    private val photosObject = Photos(0, listOf())

    @Test fun jsonToPhotos() {
        assertEquals(Converters().photosStr2Obj(photosStr),photosObject)
    }

    @Test fun photos2Json(){
        assertEquals(Converters().photosObj2Str(photosObject),photosStr)
    }

    private val categoryStr ="[{\"icon\":{\"prefix\":\"https://ss3.4sqi.net/img/categories_v2/food/thai_\",\"suffix\"" +
            ":\".png\"},\"id\":\"4bf58dd8d48988d149941735\",\"name\":\"Thai Restaurant\",\"pluralName\"" +
            ":\"Thai Restaurants\",\"primary\":true,\"shortName\":\"Thai\"}]"
    private val iconObject = Icon("https://ss3.4sqi.net/img/categories_v2/food/thai_", ".png")
    private val categoryObject = mutableListOf(Category(iconObject,"4bf58dd8d48988d149941735","Thai Restaurant"
        ,"Thai Restaurants",true,"Thai"))

    @Test fun json2Category(){
        assertEquals(Converters().categoryStr2Obj(categoryStr),categoryObject)
    }

    @Test fun category2Json(){
        assertEquals(Converters().categoryObj2Str(categoryObject),categoryStr)
    }

}