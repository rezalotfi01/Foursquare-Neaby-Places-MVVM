package com.reza.mymvvm.pages.placedetails.ui


import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.drawable.DrawableCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import coil.api.load
import com.google.android.material.snackbar.Snackbar
import com.reza.mymvvm.MainActivity
import com.reza.mymvvm.R
import com.reza.mymvvm.data.Result
import com.reza.mymvvm.databinding.FragmentDetailBinding
import com.reza.mymvvm.di.Injectable
import com.reza.mymvvm.pages.placedetails.data.Detail
import com.reza.mymvvm.util.extensions.hide
import com.reza.mymvvm.util.extensions.injectViewModel
import com.reza.mymvvm.util.extensions.show
import javax.inject.Inject


/**
 * A simple [Fragment] subclass.
 */
class DetailFragment : Fragment(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: DetailViewModel

    private val args: DetailFragmentArgs by navArgs()
    private lateinit var binding: FragmentDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        initViewModel()
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_detail, container, false
        )
        binding.viewModel = viewModel
        binding.executePendingBindings()

        initUi()

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        subscribeToUI()
    }

    private fun initViewModel() {
        viewModel = injectViewModel(viewModelFactory)
        viewModel.id = args.id
    }

    private fun initUi() {
        (activity as MainActivity).setSupportActionBar(binding.toolbar)
        (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as MainActivity).supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    private fun subscribeToUI() {
        viewModel.detail.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Result.Status.SUCCESS -> {
                    if (it.data == null){
                        return@Observer
                    }
                    binding.progressBar.hide()
                    binding.layoutData.show()
                    bindView(it.data)
                }
                Result.Status.LOADING -> binding.progressBar.show()
                Result.Status.ERROR -> {
                    binding.progressBar.hide()
                    Snackbar.make(binding.mainLayout, getString(R.string.cannot_connect_to_server), Snackbar.LENGTH_LONG).show()
                }
            }
        })
    }

    private fun bindView(detail: Detail) {
        with(binding) {
            imgCategory.load(
                detail.categories?.get(0)?.icon?.prefix + "512" + detail.categories?.get(0)?.icon?.suffix
            )
            imageView.load(detail.bestPhoto?.prefix + "512" + detail.bestPhoto?.suffix) {
                placeholder(R.drawable.ic_placeholder)
            }
            txtName.text = detail.name
            val address =
                detail.location.address + " " + detail.location.city + " " + detail.location.state + " " + detail.location.country
            txtAddress.text = address

            tagRating.text = detail.rating.toString()
            if (!detail.ratingColor.isNullOrEmpty()) {
                // Set rating color to Tag Rating
                DrawableCompat.setTint(
                    DrawableCompat.wrap(binding.tagRating.background),
                    Color.parseColor("#${detail.ratingColor}")
                )
            }

            btnCall.setOnClickListener { openDialerApp(detail.contact.phone) }

            btnMap.setOnClickListener {
                openMap(detail.location.lat,detail.location.lng)
            }
        }

    }

    private fun openDialerApp(phoneNumber: String){
        val intent = Intent(Intent.ACTION_DIAL)
        intent.data = Uri.parse("tel:$phoneNumber")
        startActivity(intent)
    }


    private fun openMap(lat: Double, long: Double){
        if (lat ==  0.0 || long == 0.0){
            Snackbar.make(binding.mainLayout,getString(R.string.location_incorrect),Snackbar.LENGTH_LONG).show()
            return
        }
        val uri = "http://maps.google.com/maps?q=loc:$lat,$long"
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
        intent.setPackage("com.google.android.apps.maps")
        startActivity(intent)
    }
}