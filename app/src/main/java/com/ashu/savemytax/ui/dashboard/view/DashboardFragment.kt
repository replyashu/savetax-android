package com.ashu.savemytax.ui.dashboard.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ashu.savemytax.data.SalaryData
import com.ashu.savemytax.data.SalaryResponse
import com.ashu.savemytax.databinding.FragmentDashboardBinding
import com.ashu.savemytax.ui.dashboard.DashboardViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null

    var dashboardAdapter: DashboardAdapter? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dashboardViewModel =
            ViewModelProvider(this).get(DashboardViewModel::class.java)

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val bundle = arguments?.getSerializable("salary_components") as List<SalaryResponse>?

        bundle?.let {
            initUIView(bundle)
        }

//        if (!bundle!!.isEmpty) {
//            Log.d("gotcha", bundle.toString())
//        }
        val textView: TextView = binding.textDashboard
        dashboardViewModel.text.observe(viewLifecycleOwner) {
            textView.text = "Salary Breakup"
        }
        return root
    }

    private fun initUIView(map: List<SalaryResponse>?) {

        dashboardAdapter = DashboardAdapter(map)

        binding.recyclerSalaryComponents.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = dashboardAdapter
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}