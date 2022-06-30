package com.ainsigne.data.local.datasource.time

import com.ainsigne.common.utils.TARGET_MINUTES

class TimeLocalSourceImpl : TimeLocalSource {

    override fun getCurrentTime(timeSource: TimeSource): Long {
        return when (timeSource) {
            TimeSource.FORCE_REFRESH -> System.currentTimeMillis() - ((TARGET_MINUTES + 1) * 60 * 1000)
            else -> System.currentTimeMillis()
        }
    }
}
