package com.reza.mymvvm.pages.placeslist.ui

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.reza.mymvvm.R
import com.reza.mymvvm.databinding.FragmentPlacesBinding
import com.reza.mymvvm.di.Injectable
import com.reza.mymvvm.services.LocationTrackingService
import com.reza.mymvvm.util.*
import com.reza.mymvvm.util.common.ConnectivityUtil
import com.reza.mymvvm.util.extensions.*
import com.reza.mymvvm.util.permission.PermissionUtil
import javax.inject.Inject

class PlacesFragment : Fragment(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: PlacesViewModel

    private lateinit var binding: FragmentPlacesBinding

    private val adapter: PlacesListAdapter by lazy { PlacesListAdapter() }

    private lateinit var locationReceiver: BroadcastReceiver

    var isObserversInit = false

    private val permissionUtil by lazy {
        PermissionUtil(
            this, arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION
                , Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        initViewModel()

        binding = FragmentPlacesBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.executePendingBindings()

        initUi()

        subscribeToUi()

        initLocationReceiver()
        checkPermissionAndResume()

        return binding.root
    }

    private fun initViewModel() {
        viewModel = injectViewModel(viewModelFactory)
        viewModel.connectivityAvailable = ConnectivityUtil.isConnected(requireContext())
    }

    private fun initUi() {
        requireActivity().window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        requireActivity().window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);



        binding.recyclerView.adapter = adapter

        adapter.onClickItem = {
            val id = viewModel
                .placesList
                .value
                ?.get(it)!!
                .id
            val action = PlacesFragmentDirections.actionPlacesFragmentToDetailFragment(id)
            findNavController().navigate(action)
        }
    }

    private fun subscribeToUi() {
        if (isObserversInit){
            binding.progressBar.hide()
            return
        }

        viewModel.placesList.observe(viewLifecycleOwner) {
            if (it == null)
                return@observe


            adapter.submitList(it) {
                binding.progressBar.hide()
            }
        }

        viewModel.lastLocation.observe(viewLifecycleOwner) {
            viewModel.placesList.refresh()
        }
        isObserversInit = true
    }


    private fun checkPermissionAndResume() {
        if (permissionUtil.hasPermission()) {
            startLocationService()
            return
        }

        permissionUtil.denied { deniedNeverAsk ->
            Snackbar.make(
                binding.root,
                getString(R.string.permission_denied_error), Snackbar.LENGTH_LONG
            )
                .setAction(getString(R.string.grant)) {
                    if (deniedNeverAsk) {
                        permissionUtil.openAppDetailsActivity()
                    } else {
                        permissionUtil.requestAll { startLocationService() }
                    }
                }
        }

        permissionUtil.requestAll {
            startLocationService()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>
        , grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        permissionUtil.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    private fun initLocationReceiver() {
        locationReceiver = requireContext().createBroadcastReceiver { intent ->
            intent?.action
                ?.takeIf { it == ACTION_LOCATION_BROADCAST }
                ?.also {
                    val latLong = intent.getDoubleExtra(INTENT_LAT, 0.0).toString() +
                            ", " + intent.getDoubleExtra(INTENT_LONG, 0.0).toString()
                    val locName = intent.getStringExtra(INTENT_LOC_NAME)
                    viewModel.setLocationNameAddress(locName!!)
                    viewModel.setLastLocation(latLong)
                }
        }

    }

    override fun onResume() {
        super.onResume()
        requireContext().registerLocalReceiver(locationReceiver, ACTION_LOCATION_BROADCAST)
    }

    override fun onPause() {
        super.onPause()
        requireContext().unregisterLocalReceiver(locationReceiver)
    }

    private fun startLocationService() = ContextCompat.startForegroundService(
        requireContext(),
        Intent(requireContext(), LocationTrackingService::class.java).apply {
            action = SERVICE_ACTION_START
        }
    )

    private fun stopLocationService() = ContextCompat.startForegroundService(
        requireContext(),
        Intent(requireContext(), LocationTrackingService::class.java).apply {
            action = SERVICE_ACTION_STOP
        }
    )


    override fun onDestroy() {
        stopLocationService()
        super.onDestroy()
    }
}