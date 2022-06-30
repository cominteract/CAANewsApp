package com.ainsigne.domain.entities

import com.ainsigne.common.utils.extension.EMPTY

sealed class ArticleDomainEntities {
    /**
     * Article Response used in wrapping common api response for news api
     * @param status [String] set to success or error
     * @param articles [List] [Article] articles response data
     */
    data class ArticlesResponse(
        val status: String = EMPTY,
        val articles: List<Article>
    )

    /**
     * Article data that contains the content, title and author name
     * @param title [String] article title
     * @param source [ArticleSource] article source
     * @param description [String] article description
     * @param content [String] article content
     * @param author [String] author name
     * @param publishedAt [String] date published
     */
    data class Article (
        val title: String = EMPTY,
        val source: ArticleSource?,
        val author: String = EMPTY,
        val description: String = EMPTY,
        val urlToImage: String = EMPTY,
        val publishedAt: String = EMPTY,
        val content: String = EMPTY
    )


    /**
     * Article source for where the article is referenced/based
     * @param name [String] the name of the reference
     */

    data class ArticleSource (
        val id: String = EMPTY,
        val name: String = EMPTY
    )
}