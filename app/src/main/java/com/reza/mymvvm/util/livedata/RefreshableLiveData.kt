package com.reza.mymvvm.util.livedata

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Observer


class RefreshableLiveData<T>(private val source: () -> LiveData<T>) : MediatorLiveData<T>(), Observer<T> {
    private var liveData: LiveData<T>

    init {
        liveData = source()
        addSource(liveData, this)
    }

    fun refresh() {
        removeSource(liveData)
        liveData = source()
        addSource(liveData, this)
    }

    override fun onChanged(t: T) {
        value = t
    }
}