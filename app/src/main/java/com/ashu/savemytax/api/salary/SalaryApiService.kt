package com.ashu.savemytax.api.salary

import com.ashu.savemytax.data.SalaryRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface SalaryApiService {

    @POST("/salary/compute")
    suspend fun computeSalary(@Body salaryRequest: SalaryRequest): Response<Map<String, Double>>

}