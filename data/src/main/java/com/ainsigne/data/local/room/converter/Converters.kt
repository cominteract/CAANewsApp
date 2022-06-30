package com.ainsigne.data.local.room.converter

import androidx.room.TypeConverter
import com.ainsigne.domain.entities.ArticleDomainEntities
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

/**
 * Converter for custom serialization
 */
object Converters {
    /**
     * Used to convert data
     */
    private val gson = Gson()

    /**
     * Converts [String] to [List] [String]
     * @param value [String] input to be converted to [List] [String]
     * @return [List] [String] converted from [String]
     */
    @TypeConverter
    @JvmStatic
    fun fromString(value: String?): List<String> {
        val listType: Type = object : TypeToken<List<String?>?>() {}.type
        return gson.fromJson(value, listType)
    }

    /**
     * Converts [List] [String] to [String]
     * @param list [List] [String] input to be converted to [String]
     * @return [String] converted from [List] [String]
     */
    @TypeConverter
    @JvmStatic
    fun fromArrayList(list: List<String?>?): String {
        return gson.toJson(list)
    }

    /**
     * Converts [String] to [ArticleDomainEntities.ArticleSource]
     * @param value [ArticleDomainEntities.ArticleSource] input to be converted to [String]
     * @return [ArticleDomainEntities.ArticleSource] converted from [String]
     */
    @TypeConverter
    @JvmStatic
    fun fromStringArticleSource(value: String?): ArticleDomainEntities.ArticleSource? {
        val action: Type = object : TypeToken<ArticleDomainEntities.ArticleSource?>() {}.type
        return gson.fromJson(value, action)
    }

    /**
     * Converts [ArticleDomainEntities.ArticleSource] to [String]
     * @param action [ArticleDomainEntities.ArticleSource] input to be converted to [String]
     * @return [String] converted from [ArticleDomainEntities.ArticleSource]
     */
    @TypeConverter
    @JvmStatic
    fun fromAction(action: ArticleDomainEntities.ArticleSource?): String {
        return gson.toJson(action)
    }
}
