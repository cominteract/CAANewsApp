package com.ainsigne.home.ui

import android.content.Context
import com.ainsigne.common.base.ui.BaseFragment
import com.ainsigne.common.utils.extension.showError
import com.ainsigne.common.utils.network.NetworkStatus
import com.ainsigne.core.di.coreComponent
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

    }

    override fun initializeUI() {
        viewModel.refreshArticles()
        binding.recyclerArticles.adapter = adapter
    }

    override fun initializeObservers() {
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