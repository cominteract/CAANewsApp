package com.ainsigne.common.base.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.ainsigne.common.base.domain.ActionBarState
import com.ainsigne.common.base.domain.BaseDataEntities
import com.ainsigne.common.base.domain.SnackToastMessage
import com.ainsigne.common.base.interfaces.NavigationCallback
import com.ainsigne.common.base.interfaces.StatusCallback
import com.ainsigne.common.base.interfaces.UITransitionCallback
import com.ainsigne.common.utils.DEFAULT_DELAY
import com.ainsigne.common.utils.extension.EMPTY
import com.ainsigne.common.utils.extension.activity
import com.ainsigne.common.utils.extension.needsRefresh
import com.ainsigne.common.utils.extension.noRefresh
import com.ainsigne.common.utils.extension.setOnSingleClickListener
import com.ainsigne.common.utils.extension.setVisible
import com.ainsigne.common.utils.extension.showError
import com.ainsigne.common.utils.extension.toggleKeyboard
import com.ainsigne.common.utils.extension.willFinish
import com.ainsigne.common.utils.network.NetworkConnected
import com.ainsigne.common.utils.network.NetworkStateManager
import com.ainsigne.common.utils.network.NetworkStatus
import com.ainsigne.common.utils.placeholder
import com.ainsigne.common.utils.ui.DelayHelper

/**
 * Common fragment setup with utility to hide/show keyboard
 * activity reference and easily show toasts/snackbars
 */
abstract class BaseFragment<VB : ViewBinding>(
    private val setUpViewBinding: (LayoutInflater) -> VB,
    private val actionBarState: ActionBarState = ActionBarState.HOME,
) : Fragment(),
    NetworkConnected,
    StatusCallback {

    /**
     * Binding for any fragment must follow a certain template
     * Please see any fragments from the features
     * features/home etc
     */
    lateinit var binding: VB

    /**
     * Navigation callback to be used in navigating between fragment/views
     */
    var navigationCallback: NavigationCallback? = null

    /**
     * Ui Transition callback to be used ui related callbacks
     */
    var uiTransitionCallback: UITransitionCallback? = null

    var maxRetry = 0

    /**
     * Initiazes ui transition callback and swipe refresh to disabled by default
     */
    override fun onResume() {
        super.onResume()
        maxRetry = 0

        activity().setupActionbar(
            hasActionBar = actionBarState == ActionBarState.CHILD_TOP || actionBarState == ActionBarState.CHILD,
            hasHomeBar = actionBarState == ActionBarState.HOME,
            hasBackButton = actionBarState == ActionBarState.CHILD,
            title = actionBarTitle(),
            backPressedBlock = backButtonPressed()
        )
        this.uiTransitionCallback = activity()
        this.uiTransitionCallback?.setupRefresh(true)
        this.uiTransitionCallback?.onSwipeRefresh {
            if (initializeToBeRefresh() != noRefresh()) {
                initializeToBeRefresh().invoke()
            } else {
                activity().stopRefresh()
            }
        }
    }

    /**
     * Initializes the navigation callback and setups the network connectivity once
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (activity() is NavigationCallback) {
            navigationCallback = activity() as NavigationCallback
        }
        NetworkStateManager.instance?.setupNetworkConnected(
            this, this, showNetworkIssue()
        )
    }

    /**
     * Deinitialize the ui transition callback
     */
    override fun onPause() {
        uiTransitionCallback?.stopRefresh()
        toggleLoading(false)
        super.onPause()
        uiTransitionCallback = null
    }

    /**
     * Setup the initialize ui to be used after view created
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeUI()
        initializeObservers()
        activity().updateStatusBarColor(
            window = activity().window,
            color = com.ainsigne.ui.R.color.text_dropdown_selected_primary
        )
        val viewClickable = if (binding.root is NestedScrollView) {
            (binding.root as NestedScrollView).getChildAt(0)
        } else {
            binding.root
        }
        viewClickable.setOnSingleClickListener {
            toggleKeyboard(false)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = setUpViewBinding(inflater)
        return binding.root
    }

    /**
     * Shows the network issue by default
     * @return [Boolean] set to true to default allow showing network issue for each fragment
     */
    override fun showNetworkIssue(): Boolean {
        return true
    }

    /**
     * To be used for loading state
     * like showing progress bars etc
     */
    fun toggleLoading(shown: Boolean) {
        activity().progressBar?.setVisible(shown, true)
    }

    /**
     * Template to be used for initializing ui
     */
    abstract fun initializeUI()

    /**
     * Template to be used for initializing observer
     */
    abstract fun initializeObservers()

    abstract fun initializeToBeRefresh(): () -> Unit

    /**
     * When internet is lost callback
     */
    override fun onInternetLost() {
        if (isAdded) {
            activity().show(
                color = com.google.android.material.R.color.error_color_material_light,
                snackToastMessage = SnackToastMessage.NO_INTERNET,
                useToast = false
            )
        }
    }

    /**
     * When internet is found callback
     */
    override fun onInternetFound() {
        if (isAdded) {
            activity().show(
                color = com.google.android.material.R.color.accent_material_light,
                snackToastMessage = SnackToastMessage.INTERNET_FOUND,
                useToast = false
            )
        }
    }

    /**
     * Required to propagate common behaviors
     */
    override fun handleStatus(vmObserverStatuses: List<BaseDataEntities.Status>) {
        vmObserverStatuses.forEachIndexed { index, status ->
            status.observer.observe(viewLifecycleOwner) { result ->
                if (isAdded) {
                    when (result) {
                        is NetworkStatus.Success -> {
                            toggleLoading(false)
                            uiTransitionCallback?.stopRefresh()
                            status.successBlock?.invoke(result)
                        }
                        is NetworkStatus.Error -> {
                            toggleLoading(false)
                            uiTransitionCallback?.stopRefresh()
                            handleStatusError(result, status)
                        }
                        is NetworkStatus.Loading -> {
                            if (uiTransitionCallback?.isRefreshing() != true) {
                                toggleLoading(true)
                            }
                        }
                        else -> {}
                    }
                }
            }
        }
    }

    private fun handleStatusError(result: NetworkStatus<*>, status: BaseDataEntities.Status) {
        result.code?.let { code ->
            if (status.isHandlingError) {
                handleErrorResult(result, status.isNeedToRefresh)
            }
        } ?: kotlin.run {
            if (status.isHandlingError) {
                handleErrorResult(result, status.isNeedToRefresh)
            }
        }
    }

    private fun handleErrorResult(result: NetworkStatus<*>, hasRefresh: Boolean) {
        when {
            needsRefresh(result, hasRefresh) -> {
                activity().hidewNotifyBar()
                val fragmentSize = activity().supportFragmentManager.fragments.size
                val errorMessage = result.errorMessage.orEmpty()
                showError(errorMessage = errorMessage) {
                    if (willFinish(fragmentSize)) {
                        maxRetry = 0
                        activity().finish()
                    } else {
                        uiTransitionCallback?.startRefresh()
                        DelayHelper.delayed(DEFAULT_DELAY) {
                            initializeToBeRefresh().invoke()
                        }
                    }
                    maxRetry++
                }
            }
            else -> {
                showError(errorMessage = result.errorMessage.orEmpty())
            }
        }
    }

    override fun actionBarTitle(): String = EMPTY

    override fun backButtonPressed() = placeholder()
}
