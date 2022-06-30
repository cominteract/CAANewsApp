package com.ainsigne.home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ainsigne.common.utils.network.NetworkStatus
import com.ainsigne.common.utils.ui.viewmodel.SingleLiveEvent
import com.ainsigne.domain.entities.ArticleDomainEntities
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

    private val _countryCodeLiveData = SingleLiveEvent<NetworkStatus<String>>()
    val countryCodeLiveData: LiveData<NetworkStatus<String>> = _countryCodeLiveData

    private val _countryCodeSavedLiveData = SingleLiveEvent<NetworkStatus<Boolean>>()
    val countryCodeSavedLiveData: LiveData<NetworkStatus<Boolean>> = _countryCodeSavedLiveData

    private val _articlesLiveData = SingleLiveEvent<NetworkStatus<List<ArticleDomainEntities.Article>>>()
    val articlesLiveData: LiveData<NetworkStatus<List<ArticleDomainEntities.Article>>> = _articlesLiveData

    fun saveCountryCode(countryCode: String) {
        viewModelScope.launch(
            CoroutineExceptionHandler { _, error ->
                _countryCodeSavedLiveData.postValue(NetworkStatus.Error(error.message))
            }
        ) {
            appRepository.saveCountryCode(countryCode).collect {
                _countryCodeSavedLiveData.postValue(it)
                forceRefreshArticles()
            }
        }
    }

    fun refreshCountryCode() {
        viewModelScope.launch(
            CoroutineExceptionHandler { _, error ->
                _countryCodeLiveData.postValue(NetworkStatus.Error(error.message))
            }
        ) {
            appRepository.getCountryCode().collect {
                _countryCodeLiveData.postValue(it)
                forceRefreshArticles()
            }
        }
    }

    /**
     * Refresh articles
     */
    fun refreshArticles() {
        viewModelScope.launch(
            CoroutineExceptionHandler { _, error ->
                _articlesLiveData.postValue(NetworkStatus.Error(error.message))
            }
        ) {
            headlineRepository.getHeadlines().collect {
                _articlesLiveData.postValue(it)
            }
        }
    }

    /**
     * Force efresh articles
     */
    fun forceRefreshArticles() {
        viewModelScope.launch(
            CoroutineExceptionHandler { _, error ->
                _articlesLiveData.postValue(NetworkStatus.Error(error.message))
            }
        ) {
            headlineRepository.forceRefreshHeadlines().collect {
                if (it) {
                    headlineRepository.getHeadlines().collect {
                        _articlesLiveData.postValue(it)
                    }
                }
            }
        }
    }
}
