package com.ashu.savemytax.data

data class SalaryRequest(
    val userId: String? = null,
    val ctc: Long? = null,
    val longitude: Double? = null,
    val latitude: Double? = null,
    val optedForOldRegime: Boolean = false,
    val optedFor12Pf: Boolean = false
)
