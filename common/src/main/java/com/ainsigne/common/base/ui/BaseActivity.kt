package com.ainsigne.common.base.ui

import android.content.Context
import android.view.MenuItem
import android.view.View
import android.view.View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
import android.view.Window
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.ainsigne.common.base.domain.SnackToastMessage
import com.ainsigne.common.base.interfaces.UITransitionCallback

/**
 * Common activity setup with utility to hide/show keyboard
 * and easily show toasts/snackbars
 */
abstract class BaseActivity : AppCompatActivity(), UITransitionCallback {

    /**
     * Progress bar to be used for all screens
     */
    var progressBar: ProgressBar? = null

    /**
     * Anchor view to be used in anchoring the snack bar on top of this view
     */
    private var anchorView: View? = null

    /**
     * Shows toast message from provided input message
     * @param snackToastMessage [SnackToastMessage] to identify which message will be displayed
     * can be updated
     */
    private fun showMessage(snackToastMessage: SnackToastMessage) {
        val message = getString(snackToastMessage.message)
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    /**
     * Shows snack bar from provided input message and text color
     * @param toastMessage [SnackToastMessage] to identify which message will be displayed
     * can be updated
     * @param color [Int] input to set the text color
     * @param isProgressShown [Boolean] flag to identify whether snackbar needs progress bar
     */
    abstract fun showNotifyBar(
        toastMessage: SnackToastMessage,
        @ColorRes color: Int,
        isRefreshShown: Boolean = false,
        block: (() -> Unit)? = null,
    )

    /**
     * Hide snack bar from provided input message and text color
     */
    abstract fun hidewNotifyBar()

    /**
     * Toggle to shows snack bar or toast from provided input message and text color
     * @param toastMessage [SnackToastMessage] to identify which message will be displayed
     * can be updated
     * @param color [Int] input to set the text color for snack bar
     * @param useToast [Boolean] input to toggle between showing toast message or snackbar
     */
    fun show(
        snackToastMessage: SnackToastMessage,
        useToast: Boolean,
        @ColorRes color: Int,
        block: (() -> Unit)? = null,
    ) {
        if (useToast) showMessage(snackToastMessage) else showNotifyBar(
            toastMessage = snackToastMessage,
            color = color,
            block = block,
            isRefreshShown = snackToastMessage == SnackToastMessage.INTERNET_FOUND
        )
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return false
    }

    /**
     * Setup which view will the internet connectivity show above
     * @param view [View] the view to anchor the snackbar with
     */
    fun setupAnchorView(view: View) {
        anchorView = view
    }

    @Suppress("DEPRECATION")
    fun invertInsets(darkTheme: Boolean, window: Window) {
        if (!darkTheme) {
            window.decorView.systemUiVisibility = SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            window.statusBarColor = ContextCompat.getColor(this, com.ainsigne.ui.R.color.white)
        } else {
            window.decorView.systemUiVisibility = 0
            window.statusBarColor = ContextCompat.getColor(this, com.ainsigne.ui.R.color.white)
        }
    }

    fun updateStatusBarColor(window: Window, color: Int) {
        window.decorView.systemUiVisibility = 0
        window.statusBarColor = ContextCompat.getColor(
            this,
            color
        )
    }

    /**
     * Shows/hides android keyboard
     */
    fun toggleKeyboard(shown: Boolean) {
        if (this.currentFocus is EditText) {
            val view = this.currentFocus
            val inputMethodManager: InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE)
                as InputMethodManager
            if (shown) {
                inputMethodManager.showSoftInput(
                    view,
                    InputMethodManager.HIDE_IMPLICIT_ONLY
                )
            } else {
                inputMethodManager.hideSoftInputFromWindow(view?.windowToken, 0)
                (this.currentFocus as EditText).clearFocus()
            }
        }
    }
}
