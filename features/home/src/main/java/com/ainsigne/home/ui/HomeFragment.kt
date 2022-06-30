package com.ainsigne.home.ui

import android.content.Context
import com.ainsigne.common.Navigation
import com.ainsigne.common.base.ui.BaseFragment
import com.ainsigne.common.navigation.HomeNavigation
import com.ainsigne.common.utils.CANADA
import com.ainsigne.common.utils.US
import com.ainsigne.common.utils.extension.setOnSingleClickListener
import com.ainsigne.common.utils.extension.showError
import com.ainsigne.common.utils.network.NetworkStatus
import com.ainsigne.core.di.coreComponent
import com.ainsigne.domain.navigation.ArticleDetails
import com.ainsigne.home.R
import com.ainsigne.home.databinding.FragmentHomeBinding
import com.ainsigne.home.di.DaggerHomeComponent
import com.ainsigne.home.ui.adapter.HeadlineAdapter
import com.ainsigne.home.viewmodel.HomeViewModel
import javax.inject.Inject

class HomeFragment : BaseFragment<FragmentHomeBinding>(
    FragmentHomeBinding::inflate
) {

    @Inject
    lateinit var viewModel: HomeViewModel

    val adapter = HeadlineAdapter {
        navigationCallback?.navigateWith(
            Navigation.Home(HomeNavigation.HOME_TO_DETAILS),
            ArticleDetails(
                title = it.title,
                author = it.author,
                description = it.description,
                content = it.content,
                urlToImage = it.urlToImage,
                publishedAt = it.publishedAt
            )
        )
    }

    override fun initializeUI() {
        viewModel.refreshArticles()
        viewModel.refreshCountryCode()
        binding.recyclerArticles.adapter = adapter
        binding.buttonToggleCanada.setOnSingleClickListener {
            viewModel.saveCountryCode(CANADA)
        }
        binding.buttonToggleUs.setOnSingleClickListener {
            viewModel.saveCountryCode(US)
        }
    }

    override fun initializeObservers() {
        viewModel.countryCodeLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is NetworkStatus.Success -> {
                    if (it.data == CANADA) {
                        binding.buttonToggleCountry.check(
                            R.id.button_toggle_canada
                        )
                    } else {
                        binding.buttonToggleCountry.check(
                            R.id.button_toggle_us
                        )
                    }
                }
            }
        }
        viewModel.articlesLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is NetworkStatus.Success -> {
                    toggleLoading(false)
                    uiTransitionCallback?.stopRefresh()
                    adapter.updateList(it.data.orEmpty())
                }
                is NetworkStatus.Error -> {
                    toggleLoading(false)
                    showError(
                        it.errorMessage.orEmpty()
                    )
                }
                is NetworkStatus.Loading -> {
                    if (uiTransitionCallback?.isRefreshing() == false) {
                        toggleLoading(true)
                    }
                }
            }
        }
    }

    override fun initializeToBeRefresh(): () -> Unit {
        return { viewModel.forceRefreshArticles() }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        DaggerHomeComponent.factory().create(coreComponent()).inject(this)
    }
}
