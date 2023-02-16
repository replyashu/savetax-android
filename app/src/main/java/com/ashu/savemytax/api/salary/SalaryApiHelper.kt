package com.ashu.savemytax.api.salary

import com.ashu.savemytax.data.SalaryRequest
import retrofit2.Response

interface SalaryApiHelper {
    suspend fun sendSalaryForComputation(salaryRequest: SalaryRequest): Response<Map<String, Double>>
}