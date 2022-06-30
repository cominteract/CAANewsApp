package com.ainsigne.common.utils.ui

import android.os.Handler
import android.os.Looper
import java.util.Timer
import kotlin.concurrent.scheduleAtFixedRate

/**
 * Delay Helper allows delay actions.
 * Can also be used to have multiple delayed actions
 */
object DelayHelper {

    val handler = Handler(Looper.getMainLooper())
    /**
     * Repeats the delay for the action
     * @param delay [Long] how long before action will be executed
     * @param action [Unit] action that will be executed at a given time
     */
    fun repeatDelayed(delay: Long, action: () -> Unit) {
        handler.postDelayed(
            delayRunnable(delay, action),
            delay
        )
    }

    fun delayRunnable(delay: Long, action: () -> Unit): Runnable {
        return object : Runnable {
            override fun run() {
                action()
                handler.postDelayed(this, delay)
            }
        }
    }

    fun resetDelay(delay: Long, action: () -> Unit) {
        handler.removeCallbacksAndMessages(null)
        repeatDelayed(delay, action)
    }

    /**
     * Adds a delay for the action
     * @param delay [Long] how long before action will be executed
     * @param action [Unit] action that will be executed at a given time
     */
    fun delayed(delay: Long, action: () -> Unit) {
        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed({ action() }, delay)
    }


}
