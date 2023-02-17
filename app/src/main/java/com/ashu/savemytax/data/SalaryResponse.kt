package com.ashu.savemytax.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.io.Serializable

@Parcelize
data class SalaryResponse (
    val componentName: String? = null,
    val componentAmount: Double = 0.0,
    val isRequired: Boolean = false,
    val isProofRequired: Boolean = false
): Parcelable