package com.ainsigne.common.utils.extension

import android.app.AlertDialog
import android.content.Intent
import com.ainsigne.common.R
import com.ainsigne.common.base.ui.BaseActivity
import com.ainsigne.common.base.ui.BaseFragment
import com.ainsigne.common.utils.CONNECT_EXCEPTION
import com.ainsigne.common.utils.MAX_RETRY_LIMIT
import com.ainsigne.common.utils.SOCKET_TIME_OUT_EXCEPTION
import com.ainsigne.common.utils.network.NetworkStatus
import com.ainsigne.ui.dialog.ConfirmationDialogFragment
import com.ainsigne.ui.domain.DialogData
import timber.log.Timber

var confirmationDialogFragment: ConfirmationDialogFragment? = null

/**
 * Shows the dialog for a base fragment
 * @param message [String] message to display
 * @param yesBlock [Unit] block to invoke if yes is pressed
 * @param noBlock [Unit] block to invoke if no is pressed
 */
fun BaseFragment<*>.showDialog(
    message: String,
    yesBlock: () -> Unit,
    noBlock: () -> Unit
) {
    val builder: AlertDialog.Builder = AlertDialog.Builder(activity())
    builder.setTitle(EMPTY)
    builder.setMessage(message)
    builder.setPositiveButton(
        getString(R.string.button_yes)
    ) { dialog, which ->
        yesBlock.invoke()
        dialog.dismiss()
    }
    builder.setNegativeButton(
        getString(R.string.button_no)
    ) { dialog, which ->
        noBlock.invoke()
        dialog.dismiss()
    }
    builder.create().show()
}

/**
 * Note : this will return an error when activity
 * is not a [BaseActivity]. Ideally we want all activities
 * to be referenced as [BaseActivity]
 * Utility for activity reference as [BaseActivity]
 * @return [BaseActivity] activity reference with casting
 */
fun BaseFragment<*>.activity(): BaseActivity {
    return requireActivity() as BaseActivity
}

fun BaseFragment<*>.restart() {
    val intent = Intent(requireContext(), requireActivity()::class.java)
    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
    startActivity(intent)
    Runtime.getRuntime().exit(0)
}

/**
 * Shows/Hides android keyboard when shown
 * Uses [BaseActivity] utility so parent activity must be the same
 * @param shown [Boolean] toggle when keyboard needs to be shown or not
 */
fun BaseFragment<*>.toggleKeyboard(shown: Boolean) {
    activity().toggleKeyboard(shown)
}

/**
 * Confirmation dialog default when it does not need to be injected
 * @param confirmationData [DialogData] data to populate the dialog
 */
fun getConfirmationDialogDefault(confirmationData: DialogData?): ConfirmationDialogFragment? = confirmationData?.let {
    ConfirmationDialogFragment(
        it
    )
} ?: run {
    null
}

fun BaseFragment<*>.willFinish(fragmentSize: Int): Boolean {
    return maxRetry >= MAX_RETRY_LIMIT && fragmentSize == 1
}

fun BaseFragment<*>.needsRefresh(result: NetworkStatus<*>, hasRefresh: Boolean): Boolean {
    return hasRefresh &&
        (
            result.errorMessage == SOCKET_TIME_OUT_EXCEPTION ||
                result.errorMessage == CONNECT_EXCEPTION
            )
}

fun BaseFragment<*>.noRefresh(): () -> Unit {
    return { Timber.d(" Nothing to refresh ") }
}
