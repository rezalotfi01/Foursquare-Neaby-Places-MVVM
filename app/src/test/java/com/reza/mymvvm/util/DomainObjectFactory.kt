package com.reza.mymvvm.util

import com.reza.mymvvm.pages.placeslist.data.Place


object DomainObjectFactory {

    fun createPlace() = mock<Place>()

    fun createPlacesList(count: Int): List<Place> {
        return (0 until count).map {
            createPlace()
        }
    }

}
