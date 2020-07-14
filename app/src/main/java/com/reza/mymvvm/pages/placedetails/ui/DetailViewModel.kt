package com.reza.mymvvm.pages.placedetails.ui

import androidx.lifecycle.ViewModel
import com.reza.mymvvm.pages.placedetails.data.DetailRepository
import javax.inject.Inject


/**
 * The ViewModel used in [DetailFragment].
 */
class DetailViewModel @Inject constructor(private val repository: DetailRepository) : ViewModel() {

    lateinit var id: String

    val detail by lazy { repository.observeDetail(id) }

}