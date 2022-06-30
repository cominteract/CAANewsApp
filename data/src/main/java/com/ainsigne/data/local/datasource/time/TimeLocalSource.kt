package com.ainsigne.data.local.datasource.time

enum class TimeSource {
    CURRENT,
    BEFORE_CURRENT,
    FETCH_OR_CLEAR,
    AFTER_SAVE,
    FORCE_REFRESH
}
interface TimeLocalSource {

    fun getCurrentTime(timeSource: TimeSource): Long
}
