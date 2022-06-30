package com.ainsigne.home.di

import com.ainsigne.common.scopes.Fragment
import com.ainsigne.core.di.components.CoreComponent
import com.ainsigne.home.ui.HomeFragment
import dagger.Component

@Component(
    dependencies = [CoreComponent::class],
    modules = [HomeViewModelModule::class]
)
@Fragment
interface HomeComponent {

    @Component.Factory
    interface Factory {
        fun create(component: CoreComponent): HomeComponent
    }

    fun inject(homeFragment: HomeFragment)
}
