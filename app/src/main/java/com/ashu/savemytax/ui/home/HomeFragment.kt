package com.ashu.savemytax.ui.home

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.ashu.savemytax.databinding.FragmentHomeBinding
import com.ashu.savemytax.utils.ManagePermissions
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val viewModel by viewModels<HomeViewModel>()

    private val PermissionsRequestCode = 123
    private lateinit var managePermissions: ManagePermissions

    private val REQUEST_CODE = 100

    private var ctc: Long = 0
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private var userLocation: Location? = null

    val sharedpreferences by lazy { requireContext().getSharedPreferences("preference_key", Context.MODE_PRIVATE) }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val list = listOf(
            Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())

        managePermissions = ManagePermissions(requireActivity(),list,PermissionsRequestCode)

        initUI()
        return root
    }

    private fun initUI() {

        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            locationPermissionRequest.launch(arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION))
        } else {
            fetchLocation()
        }

        val amnt = binding.editCtc.editableText.toString()
        if (amnt.isEmpty()) {
            ctc = 0
        } else {
            ctc = amnt.toLong()
        }

        val userId = sharedpreferences.getString("user_uuid", null)
        binding.buttonComputeBreakup.setOnClickListener {
            viewModel.fetchSalaryBreakupDetails(userId, userLocation, ctc)
        }
    }

    val locationPermissionRequest = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        when {
            permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {
                // Precise location access granted.
                Log.d("locationaccess", permissions.toString())
                fetchLocation()
            }
            permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                // Only approximate location access granted.
                fetchLocation()
            } else -> {
            // No location access granted.
        }
        }
    }

    private fun fetchLocation() {
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location : Location? ->
                // Got last known location. In some rare situations this can be null.
                Log.d("locationacquired", location.toString())
                userLocation = location
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}