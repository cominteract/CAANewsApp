package com.ainsigne.caa_newsapp

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ProgressBar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.fragment.NavHostFragment
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.ainsigne.caa_newsapp.navigation.NavigationCollection
import com.ainsigne.caa_newsapp.navigation.NavigationCoordinator
import com.ainsigne.caa_newsapp.navigation.NavigationCoordinatorViewModel
import com.ainsigne.common.Navigation
import com.ainsigne.common.base.domain.SnackToastMessage
import com.ainsigne.common.base.interfaces.NavigationCallback
import com.ainsigne.common.base.ui.BaseActivity
import com.ainsigne.common.utils.extension.center
import com.ainsigne.common.utils.extension.toPx
import com.ainsigne.common.utils.placeholder

/**
 * Current main entry point for all fragments/views
 */
class MainActivity : BaseActivity(), NavigationCallback {

    /**
     * navigation collection to modularize navigation
     */
    private var navigationCollection = NavigationCollection()

    /**
     * navigation view model used in navigating between fragments/views
     */
    private var navigationViewModel = NavigationCoordinatorViewModel()

    /**
     * setups the navigation view model to be initialized
     * @param navigationViewModel [NavigationCoordinatorViewModel] the viewmodel to initialized with
     * @param navigationCollection [NavigationCollection] collection to modularize navigation
     */
    fun setupNavigationViewModel(
        navigationViewModel: NavigationCoordinatorViewModel,
        navigationCollection: NavigationCollection
    ) {
        this.navigationViewModel = navigationViewModel
        this.navigationCollection = navigationCollection
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        progressBar = ProgressBar(this, null, android.R.attr.progressBarStyleSmall)
        progressBar?.center(
            50f.toPx.toInt(),
            50f.toPx.toInt()
        )
        findViewById<ConstraintLayout>(R.id.container_main).addView(progressBar)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.container) as NavHostFragment
        val navController = navHostFragment.navController

        navigationViewModel.navigation.observe(this) { navigation ->
            when (navigation) {
                is NavigationCoordinator.PopBackStack -> navController.popBackStack()
                is NavigationCoordinator.NavigateUp -> navController.navigateUp()
                is NavigationCoordinator.PopBackStackSpecific -> { navController.popBackStack(navigation.destinationId, navigation.inclusive) }
                else -> navigation?.navDirections?.also {
                    navController.navigate(it)
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    /**
     * When swipe refresh is initiated must perform the action included
     * @param action [Unit] must be a function that will perform when swipe refreshed
     */
    override fun onSwipeRefresh(action: () -> Unit) {
        findViewById<SwipeRefreshLayout>(R.id.swipe_refresh_main).setOnRefreshListener(action)
    }

    override fun showNotifyBar(
        toastMessage: SnackToastMessage,
        color: Int,
        isRefreshShown: Boolean,
        block: (() -> Unit)?,
    ) {
        placeholder()
    }

    override fun hidewNotifyBar() {
        TODO("Not yet implemented")
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    /**
     * Start refresh when data needs to be manually refreshed
     */
    override fun startRefresh() {
        findViewById<SwipeRefreshLayout>(R.id.swipe_refresh_main).isRefreshing = true
    }

    /**
     * Stops swipe refresh from doing the refresh
     */
    override fun stopRefresh() {
        findViewById<SwipeRefreshLayout>(R.id.swipe_refresh_main).isRefreshing = false
    }

    override fun isRefreshing(): Boolean {
        return findViewById<SwipeRefreshLayout>(R.id.swipe_refresh_main).isRefreshing
    }

    override fun setupRefresh(hasSwipeRefresh: Boolean) {
        findViewById<SwipeRefreshLayout>(R.id.swipe_refresh_main).isEnabled = hasSwipeRefresh
    }

    override fun setupActionbar(
        title: String,
        hasActionBar: Boolean,
        hasBackButton: Boolean,
        hasHomeBar: Boolean,
        backPressedBlock: (() -> Unit)?,
    ) {
        if (hasActionBar) {
            supportActionBar?.show()
        } else {
            supportActionBar?.hide()
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(hasBackButton)
    }

    override fun setupLanguage(language: String) {
        TODO("Not yet implemented")
    }

    override fun <T> navigateWith(navigation: Navigation, data: T?) {
        when (navigation) {
            is Navigation.Splash -> {
                navigationCollection.splashNavigation(
                    navigationViewModel, navigation
                )
            }
            is Navigation.Custom -> {
                navigationCollection.customNavigation(
                    navigationViewModel, navigation, data
                )
            }
            is Navigation.Home -> {
                navigationCollection.homeNavigation(
                    navigationViewModel, navigation, data
                )
            }
        }
    }
}
