package com.ashu.savemytax.ui.home

import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ashu.savemytax.data.RegisterResponse
import com.ashu.savemytax.data.SalaryRequest
import com.ashu.savemytax.repository.salary.SalaryRepository
import com.ashu.savemytax.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val salaryRepository: SalaryRepository): ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text

    private val _result = MutableLiveData<Resource<Boolean>>()
    val result: LiveData<Resource<Boolean>>
        get() = _result

    fun fetchSalaryBreakupDetails(userId: String?, location: Location?, ctc: Long?) = viewModelScope.launch {
        _result.postValue(Resource.loading(null))

        val salaryRequest = SalaryRequest(userId, ctc, location?.longitude, location?.latitude)
        try {
            salaryRepository.sendSalaryForCompute(salaryRequest).let {
                if (it.isSuccessful) {
                    _result.postValue(Resource.success(it.body()))
                } else {
                    _result.postValue(Resource.error(it.message(), null))
                }
            }
        } catch (e: Exception) {

        }

    }
}