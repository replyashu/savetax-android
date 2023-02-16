package com.ashu.savemytax.repository.salary

import com.ashu.savemytax.api.salary.SalaryApiHelper
import com.ashu.savemytax.data.SalaryRequest
import javax.inject.Inject

class SalaryRepository @Inject constructor(private val salaryApiHelper: SalaryApiHelper) {

    suspend fun sendSalaryForCompute(salaryRequest: SalaryRequest) =
        salaryApiHelper.sendSalaryForComputation(salaryRequest)
}