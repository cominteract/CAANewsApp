package com.ainsigne.domain.navigation

import android.os.Parcelable
import com.ainsigne.common.utils.extension.EMPTY
import kotlinx.parcelize.Parcelize

/**
 * Article data to be used in details
 * @param title [String] article title
 * @param description [String] article description
 * @param content [String] article content
 * @param author [String] author name
 * @param publishedAt [String] date published
 */
@Parcelize
data class ArticleDetails(
    val title: String = EMPTY,
    val author: String? = EMPTY,
    val description: String? = EMPTY,
    val urlToImage: String? = EMPTY,
    val publishedAt: String? = EMPTY,
    val content: String? = EMPTY
) : Parcelable
