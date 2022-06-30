package com.ainsigne.splash.ui

import com.ainsigne.common.Navigation
import com.ainsigne.common.base.ui.BaseFragment
import com.ainsigne.common.navigation.SplashNavigation
import com.ainsigne.common.utils.extension.noRefresh
import com.ainsigne.common.utils.placeholder
import com.ainsigne.common.utils.ui.DelayHelper
import com.ainsigne.splash.databinding.FragmentSplashBinding

class SplashFragment : BaseFragment<FragmentSplashBinding>(
    FragmentSplashBinding::inflate
) {
    override fun initializeUI() {
        DelayHelper.delayed(5000) {
            navigationCallback?.navigateWith(
                Navigation.Splash(SplashNavigation.SPLASH_TO_HOME), null
            )
        }
    }

    override fun initializeObservers() {
        placeholder()
    }

    override fun initializeToBeRefresh(): () -> Unit {
        return noRefresh()
    }
}
