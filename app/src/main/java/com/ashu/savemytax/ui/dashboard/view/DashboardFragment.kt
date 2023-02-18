package com.ashu.savemytax.ui.dashboard.view

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ashu.savemytax.data.SalaryResponse
import com.ashu.savemytax.databinding.FragmentDashboardBinding
import com.ashu.savemytax.ui.dashboard.DashboardViewModel
import com.ashu.savemytax.utils.clickWithDebounce
import dagger.hilt.android.AndroidEntryPoint
import ir.mahozad.android.PieChart

@AndroidEntryPoint
class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null

    var dashboardAdapter: DashboardAdapter? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private var expanded: Boolean = false

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

        dashboardAdapter = DashboardAdapter(map, DashboardAdapter.OnClickListener {
            Log.d("gotresponse", it.toString())
            inflateDialog(it.componentName?.split(" ")?.first())
        })

        binding.recyclerSalaryComponents.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = dashboardAdapter
        }

        val total = map?.last()?.componentAmount
        val slices = mutableListOf<PieChart.Slice>()
        val hsvColor = floatArrayOf(0f, 1f, 1f)

        var number = 20
        for (i in 0 until map?.size!! - 1) {
            if (number == 100) {
                number = 0
            }
            hsvColor[0] = 360f * number / 100
            slices.add(PieChart.Slice((map[i].componentAmount / total!!).toFloat(), Color.HSVToColor(hsvColor),
                number, label = map[i].componentName!!, Color.BLACK, 32f, legendPercentageSize = 20f, legendPercentageColor = Color.BLACK))
            number += 10
        }
        binding.pieChartSalaryBreakup.centerLabel = "Breakup"
        binding.pieChartSalaryBreakup.isAnimationEnabled = true
        binding.pieChartSalaryBreakup.labelIconsPlacement = PieChart.IconPlacement.START
        binding.pieChartSalaryBreakup.elevation = 20f
        binding.pieChartSalaryBreakup.slices = slices
        binding.recyclerSalaryComponents.visibility = View.GONE

        binding.textShowDetail.setOnClickListener {
            val isExpanded = expanded
            if (isExpanded) {
                expanded = false
                binding.recyclerSalaryComponents.visibility = View.GONE
            } else {
                expanded = true
                binding.recyclerSalaryComponents.visibility = View.VISIBLE
            }
        }
    }

    private fun inflateDialog(name: String?) {
        when(name) {
            "HRA" ->

                return
            "Fitness" ->
                return
            "Telephone" ->
                return
            "Book" ->
                return

        }
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}