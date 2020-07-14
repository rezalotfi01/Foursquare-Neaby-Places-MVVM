package com.reza.mymvvm.pages.placeslist.ui

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.reza.mymvvm.di.CoroutineIO
import com.reza.mymvvm.pages.placeslist.data.Place
import com.reza.mymvvm.pages.placeslist.data.PlacesRepository
import com.reza.mymvvm.util.common.DateUtil
import com.reza.mymvvm.util.livedata.RefreshableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import javax.inject.Inject

class PlacesViewModel @Inject constructor(
    private val repository: PlacesRepository,
    @CoroutineIO private val ioCoroutineScope: CoroutineScope
) : ViewModel() {

    val greetingMessage = ObservableField(DateUtil.getTimeGreetingMessage())
    var locationNameAddress = ObservableField("")
        private set

    var connectivityAvailable: Boolean = false

    val lastLocation by lazy {
        repository.observeLastLatLong()
    }


    val placesList by lazy {
        RefreshableLiveData {
            if (lastLocation.value == null)
                MutableLiveData<PagedList<Place>>(null)
            else repository.observePagedList(
                connectivityAvailable, lastLocation.value!!, ioCoroutineScope
            )
        }
    }

    fun setLocationNameAddress(nameAddress: String) {
        locationNameAddress.set(
            if (nameAddress.trim().isNotEmpty())
                "You are in $nameAddress"
            else
                "Have nice day."
        )
    }


    fun setLastLocation(latLong: String) = repository.saveLastLocation(latLong)

    /**
     * Cancel coroutines when the ViewModel is cleared.
     */
    override fun onCleared() {
        super.onCleared()
        ioCoroutineScope.cancel()
    }

}