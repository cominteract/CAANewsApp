package com.ainsigne.data.repository.util

import com.ainsigne.common.utils.TARGET_MINUTES
import com.ainsigne.data.local.datasource.time.TimeLocalSource
import com.ainsigne.data.local.datasource.time.TimeSource

class FakeTimeLocalSource : TimeLocalSource {

    override fun getCurrentTime(timeSource: TimeSource): Long {
        return when (timeSource) {
            TimeSource.CURRENT -> 123456789L
            TimeSource.BEFORE_CURRENT -> 123326789L
            TimeSource.AFTER_SAVE -> 123586789L
            TimeSource.FETCH_OR_CLEAR -> 123586789L
            TimeSource.FORCE_REFRESH -> 123456789L - ((TARGET_MINUTES + 100) * 60 * 1000)
        }
    }
}
