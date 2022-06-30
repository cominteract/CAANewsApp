package com.ainsigne.home.ui

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ainsigne.common.base.ui.BaseFragment
import com.ainsigne.common.utils.extension.noRefresh
import com.ainsigne.core.di.coreComponent
import com.ainsigne.home.R
import com.ainsigne.home.databinding.FragmentHomeBinding


class HomeFragment : BaseFragment<FragmentHomeBinding>(
    FragmentHomeBinding::inflate
) {
    override fun initializeUI() {

    }

    override fun initializeObservers() {

    }

    override fun initializeToBeRefresh(): () -> Unit {
        return noRefresh()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        DaggerHomeComponent.factory().create(coreComponent()).inject(this)
    }


}