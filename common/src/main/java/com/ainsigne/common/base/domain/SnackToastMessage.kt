package com.ainsigne.common.base.domain

import com.ainsigne.common.R


enum class SnackToastMessage(var message: Int) {
    NO_INTERNET(message = R.string.internet_is_lost),
    NO_SERVER_CONNECTION(message = R.string.internet_is_lost),
    INTERNET_FOUND(message = R.string.internet_is_found)
}
