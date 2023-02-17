package com.ashu.savemytax.ui.dashboard.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ashu.savemytax.data.SalaryData
import com.ashu.savemytax.data.SalaryRequest
import com.ashu.savemytax.data.SalaryResponse
import com.ashu.savemytax.databinding.SalaryDetailItemBinding
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DashboardAdapter @Inject constructor(private val salaries: List<SalaryResponse>?):
    RecyclerView.Adapter<DashboardAdapter.DashboardViewAdapter>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DashboardViewAdapter {
        return DashboardViewAdapter(SalaryDetailItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: DashboardViewAdapter, position: Int) {
        salaries?.let {
            holder.bind(salaries[position])
        }
    }

    override fun getItemCount(): Int {
        return salaries?.size!!
    }

    inner class DashboardViewAdapter(private val binding: SalaryDetailItemBinding):
        RecyclerView.ViewHolder(binding.root) {

            fun bind(salary: SalaryResponse) {
                binding.apply {
                    textSalaryComponentName.text = salary.componentName
                    textSalaryComponentValue.text = salary.componentAmount.toString()
//                    if (salary.isProofRequired) {
//                        imageGeneratePdf.visibility = View.VISIBLE
//                    } else {
//                        imageGeneratePdf.visibility = View.GONE
//                    }
//                    if (salary.isRequired) {
//                        checkIfGiven.visibility = View.VISIBLE
//                    } else {
//                        checkIfGiven.visibility = View.GONE
//                    }
                }


            }

        }
}