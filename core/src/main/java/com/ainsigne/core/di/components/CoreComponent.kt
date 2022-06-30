package com.ainsigne.core.di.components

import android.content.Context
import com.ainsigne.domain.repository.AppRepository
import com.ainsigne.domain.repository.HeadlineRepository
import com.ainsigne.core.di.modules.CoreModule
import com.ainsigne.core.di.modules.DatabaseModule
import com.ainsigne.data.di.DataModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

/**
 * Core component modules and repository definition
 * [CoreModule] for android dependencies
 * [DataModule] for repository and data sources dependency
 * [DatabaseModule] for local sources dependency
 *
 */
@Singleton
@Component(modules = [CoreModule::class, DataModule::class, DatabaseModule::class])
interface CoreComponent {

    // Factory for binding context
    @Component.Factory
    interface Factory {
        /**
         * binds context and creates core component
         * @param context [Context] input context to bind
         */
        fun create(@BindsInstance context: Context): CoreComponent
    }


    // Headline repository definition
    val headlineRepository: HeadlineRepository

    // App repository definition
    val appRepository: AppRepository
}
