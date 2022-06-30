package com.ainsigne.home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ainsigne.common.utils.network.NetworkStatus
import com.ainsigne.common.utils.ui.viewmodel.SingleLiveEvent
import com.ainsigne.domain.repository.AppRepository
import com.ainsigne.domain.repository.HeadlineRepository
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val appRepository: AppRepository,
    private val headlineRepository: HeadlineRepository
) : ViewModel() {
    private val _countryCodeSavedLiveData = SingleLiveEvent<NetworkStatus<Boolean>>()
    val countryCodeSavedLiveData: LiveData<NetworkStatus<Boolean>> = _countryCodeSavedLiveData

    private val _countryCodeLiveData = SingleLiveEvent<NetworkStatus<String>>()
    val countryCodeLiveData: LiveData<NetworkStatus<String>> = _countryCodeLiveData


    /**
     * Refresh get country code
     */
    fun refreshCountryCode() {
        viewModelScope.launch(
            CoroutineExceptionHandler { _, error ->
                _countryCodeLiveData.postValue(NetworkStatus.Error(error.message))
            }
        ) {
            appRepository.getCountryCode().collect {
                _countryCodeLiveData.postValue(it)
            }
        }
    }
}