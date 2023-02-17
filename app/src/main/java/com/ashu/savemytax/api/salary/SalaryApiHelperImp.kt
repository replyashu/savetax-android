package com.ashu.savemytax.api.salary

import com.ashu.savemytax.data.SalaryData
import com.ashu.savemytax.data.SalaryRequest
import com.ashu.savemytax.data.SalaryResponse
import retrofit2.Response
import javax.inject.Inject

class SalaryApiHelperImp @Inject constructor(private val salaryApiService: SalaryApiService): SalaryApiHelper {

    override suspend fun sendSalaryForComputation(salaryRequest: SalaryRequest): Response<List<SalaryResponse>> =
        salaryApiService.computeSalary(salaryRequest)
}