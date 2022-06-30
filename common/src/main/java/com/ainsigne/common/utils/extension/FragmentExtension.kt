package com.ainsigne.common.utils.extension

import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import com.ainsigne.ui.R
import com.ainsigne.common.base.ui.BaseActivity
import com.ainsigne.ui.dialog.ConfirmationDialogFragment
import com.ainsigne.ui.domain.ConfirmationType
import com.ainsigne.ui.domain.DialogData
import timber.log.Timber

/**
 * Show error dialog with message
 */
fun Fragment.showError(
    errorMessage: String,
    accept: (() -> Unit)? = null
) {
    showConfirmation(
        DialogData(
            titleData = getString(R.string.text_confirmation_error_title),
            captionData = errorMessage,
            confirmationType = ConfirmationType.ERROR,
            acceptBlock = accept
        )
    )
}

/**
 * Show error dialog with message
 */
fun Fragment.showInfo(
    infoMessage: String,
    accept: (() -> Unit)? = null
) {
    showConfirmation(
        DialogData(
            titleData = getString(R.string.text_confirmation_info_title),
            captionData = infoMessage,
            confirmationType = ConfirmationType.INFO,
            acceptBlock = accept
        )
    )
}

/**
 * Show success dialog with message
 */
fun Fragment.showSuccess(
    message: String,
    accept: (() -> Unit)
) {
    showConfirmation(
        DialogData(
            titleData = getString(R.string.text_confirmation_success_title),
            captionData = message,
            confirmationType = ConfirmationType.SUCCESS,
            acceptBlock = accept
        )
    )
}

/**
 * Dismiss all dialogs in case of server or network connection issue
 */
fun BaseActivity.dismissDialogs() {
    this.supportFragmentManager.fragments.takeIf { it.isNotEmpty() }
        ?.map { (it as? ConfirmationDialogFragment)?.dismiss() }
}

/**
 * Dismiss all dialogs in case of server or network connection issue
 */
fun BaseActivity.hasOpenedDialogs(): Boolean {
    return this.supportFragmentManager.fragments.isNotEmpty()
}

/**
 * Show confirmation dialog with data
 * @param confirmationData [DialogData] data to populate the dialog
 * @param confirmationDialog [ConfirmationDialogFragment] dialog fragment to show
 */
fun Fragment.showConfirmation(
    confirmationData: DialogData?,
    confirmationDialog: ConfirmationDialogFragment? = getConfirmationDialogDefault(confirmationData),
    isCancellable: Boolean = false
) {

    confirmationData?.let { confirmation ->
        confirmationDialog?.updateDialogData(confirmation)
        if (isAdded) {
            confirmationDialog?.show(requireActivity().supportFragmentManager, "CONFIRMATION DIALOG")
        }
        confirmationDialog?.isCancelable = isCancellable
    } ?: kotlin.run {
        Timber.d(" Confirmation Data is Missing ")
    }
}

/**
 *  Support single implementation of back navigation in any Fragment
 */
inline fun Fragment.handleBackPress(crossinline event: () -> Unit) {
    requireActivity().onBackPressedDispatcher.addCallback(
        this.viewLifecycleOwner,
        object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                event.invoke()
            }
        }
    )
}
