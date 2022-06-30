package com.ainsigne.common.utils.extension

import android.os.SystemClock
import android.view.View
import android.view.View.GONE
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import com.ainsigne.common.utils.network.NetworkStatus
import com.ainsigne.common.utils.ui.DelayHelper

// Extension for setting visibility to GONE
fun View.gone() {
    this.visibility = GONE
}

// Extension for setting visibility to VISIBLE
fun View.visible() {
    this.visibility = VISIBLE
}

// Extension for setting visibility to INVISIBLE
fun View.invisible() {
    this.visibility = INVISIBLE
}

/**
 * OnClick option that throttle click event based on wait value (2000L by default)
 * @param wait [Long] how long the throttle will take place
 * @param block [Unit] the action that will be done when throttle is done
 */
fun View.setOnSingleEnableClickListener(wait: Long = 3000L, block: () -> Unit) {
    setOnClickListener(OnSingleClickListener(wait = wait, block = block, hasEnabling = true))
}

/**
 * OnClick option that throttle click event based on wait value (2000L by default)
 * @param wait [Long] how long the throttle will take place
 * @param block [Unit] the action that will be done when throttle is done
 */
fun View.setOnSingleClickListener(wait: Long = 3000L, block: () -> Unit) {
    setOnClickListener(OnSingleClickListener(wait = wait, block = block, hasEnabling = false))
}

/**
 * OnClick option that throttle click event based on observer
 * @param observer [NetworkStatus] if error or success then it should be able to click it again
 * @param block [Unit] the action that will be done when throttle is done
 * @param lifecycleOwner [LifecycleOwner] current lifecycleowner for one being observed
 */
fun <T> View.setOnSingleClickObserverListener(
    observer: LiveData<NetworkStatus<T>>,
    block: () -> Unit,
    lifecycleOwner: LifecycleOwner
) {
    setOnClickListener(OnSingleClickObserverListener(observer, block, lifecycleOwner))
}

/**
 * On Single click listener for not allowing multiple click
 * @param observer [NetworkStatus] if error or success then it should be able to click it again
 * @param block [Unit] the action that will be done when throttle is done
 * @param lifecycleOwner [LifecycleOwner] current lifecycleowner for one being observed
 */
private class OnSingleClickObserverListener<T>(
    private val observer: LiveData<NetworkStatus<T>>,
    private val block: () -> Unit,
    private val lifecycleOwner: LifecycleOwner
) :
    View.OnClickListener {
    var isObserved: Boolean = false
    override fun onClick(view: View) {
        if (observer.value is NetworkStatus.Loading && isObserved) {
            return
        }
        if (isObserved.not()) {
            observer.observe(lifecycleOwner) {
                when (it) {
                    is NetworkStatus.Success, is NetworkStatus.Error -> {
                        view.setEnable(true)
                    }
                    is NetworkStatus.Loading -> {
                        view.setEnable(false)
                    }
                }
            }
            isObserved = true
        }
        block()
    }
}

/**
 * On Single click listener for not allowing multiple click
 * @param wait [Long] how long the throttle will take place
 * @param block [Unit] the action that will be done when throttle is done
 */
private class OnSingleClickListener(private val wait: Long, private val block: () -> Unit, private val hasEnabling: Boolean) :
    View.OnClickListener {

    private var lastClickTime = 0L

    override fun onClick(view: View) {
        if (hasEnabling) {
            view.setEnable(false)
            DelayHelper.delayed(wait) {
                view.setEnable(true)
            }
        }
        if ((SystemClock.elapsedRealtime() - lastClickTime) < wait) {
            return
        }

        lastClickTime = SystemClock.elapsedRealtime()
        block()
    }
}

/**
 * Extension that centers the view within a constraint layout
 * @param width [Int] width for the view to displayed
 * @param height [Int] height for the view to be displayed
 */
fun View.center(
    width: Int = ConstraintLayout.LayoutParams.MATCH_PARENT,
    height: Int = ConstraintLayout.LayoutParams.MATCH_PARENT
) {
    val layoutParams: ConstraintLayout.LayoutParams = ConstraintLayout.LayoutParams(
        width,
        height
    )
    layoutParams.bottomToBottom = ConstraintSet.PARENT_ID
    layoutParams.endToEnd = ConstraintSet.PARENT_ID
    layoutParams.startToStart = ConstraintSet.PARENT_ID
    layoutParams.topToTop = ConstraintSet.PARENT_ID
    this.layoutParams = layoutParams
}

/**
 * Extension for toggling visibility with condition
 * @param visible [Boolean] toggle to identify visibility condition
 * when true this will display the view when false would either hide it
 * INVISIBLE or GONE
 * @param resize [Boolean] toggle to identify whether hiding it would
 * set to INVISIBLE or GONE
 */
fun View.setVisible(visible: Boolean, resize: Boolean) {
    this.visibility = if (visible) VISIBLE else if (resize) GONE else INVISIBLE
}

/**
 * Extension for toggling visibility with condition with another view
 * @param visible [Boolean] toggle to identify visibility condition
 * when true this will display the view when false would either hide it
 * INVISIBLE or GONE
 * @param resize [Boolean] toggle to identify whether hiding it would
 * set to INVISIBLE or GONE
 */
fun View.toggleVisible(visible: Boolean, resize: Boolean, view: View) {
    view.visibility = if (!visible) VISIBLE else if (resize) GONE else INVISIBLE
    this.visibility = if (visible) VISIBLE else if (resize) GONE else INVISIBLE
}

/**
 * Extension to enable the view
 * @param enable [Boolean] toggle enable for the view
 */
fun View.setEnable(enable: Boolean) {
    this.isEnabled = enable
}
