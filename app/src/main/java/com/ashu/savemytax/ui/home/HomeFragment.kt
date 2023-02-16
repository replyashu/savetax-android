package com.ashu.savemytax.ui.home

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.Selection
import android.text.TextWatcher
import android.util.Log
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.ashu.savemytax.R
import com.ashu.savemytax.databinding.FragmentHomeBinding
import com.ashu.savemytax.ui.MainActivity
import com.ashu.savemytax.ui.dashboard.DashboardFragment
import com.ashu.savemytax.ui.details.DetailViewFragment
import com.ashu.savemytax.utils.EventObserver
import com.ashu.savemytax.utils.ManagePermissions
import com.ashu.savemytax.utils.getCurrency
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.AndroidEntryPoint
import java.io.Serializable

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

    private var currency = "₹  "

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
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED) {
            locationPermissionRequest.launch(arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION))
        } else {
            fetchLocation()
        }

        binding.textWelcomeUser.text = getString(R.string.welcome_user, sharedpreferences.getString("user_name", null))
        val userId = sharedpreferences.getString("user_uuid", null)
        binding.buttonComputeBreakup.setOnClickListener {
            val amnt = binding.editCtc.editableText.toString().replace(currency, "")
            ctc = if (amnt.isEmpty()) {
                0
            } else {
                amnt.toLong()
            }
            val isOldRegime = binding.checkboxRegime.isChecked
            val optedFor12Pf = binding.checkboxPf1800.isChecked
            viewModel.fetchSalaryBreakupDetails(userId, userLocation, ctc, isOldRegime, optedFor12Pf)
        }

        binding.editCtc.apply {
            setText(currency)
            Selection.setSelection(text, this.text?.length!!)
            addTextChangedListener(object: TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                }

                override fun afterTextChanged(s: Editable?) {
                    if (!s.toString().startsWith(currency)) {
                        setText(currency);
                        Selection.setSelection(text, text?.length!!);
                    }
                }
            })
        }

        observers()
    }

    private fun observers() {

        viewModel.result.observe(viewLifecycleOwner) {
            it.data?.let { map ->
                if (map.isNotEmpty()) {
                    // Navigate to another screen
                    navigateToDashboardFragment(map)
                    for(i in map.keys) {
                        Log.d(i, map[i].toString())
                    }
                }
            }
        }
    }

    private fun navigateToDashboardFragment(map: Map<String, Double>) {
        val navController = activity?.findNavController(R.id.nav_host_fragment_activity_main)
        val fragment = DetailViewFragment()
        val bundle = Bundle()
        bundle.putSerializable("salary_components", map as Serializable)
        fragment.arguments = bundle
        val transaction = parentFragmentManager.beginTransaction()
        transaction.add(R.id.constraint_parent, fragment)
        transaction.addToBackStack("homeFragment")
        transaction.commit()
//        navController?.navigate(R.id.navigation_profile, bundle)
    }

    private val locationPermissionRequest = registerForActivityResult(
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

    @SuppressLint("MissingPermission")
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