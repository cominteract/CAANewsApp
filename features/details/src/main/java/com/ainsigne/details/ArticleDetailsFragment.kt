package com.ainsigne.details

import android.annotation.SuppressLint
import com.ainsigne.common.base.ui.BaseFragment
import com.ainsigne.common.utils.extension.loadUrl
import com.ainsigne.common.utils.extension.noRefresh
import com.ainsigne.details.databinding.FragmentArticleDetailsBinding
import com.ainsigne.domain.navigation.ArticleDetails
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


class ArticleDetailsFragment : BaseFragment<FragmentArticleDetailsBinding>(
    FragmentArticleDetailsBinding::inflate
) {
    @SuppressLint("SimpleDateFormat")
    override fun initializeUI() {
        binding.apply {
            val key = arguments?.keySet()?.firstOrNull().orEmpty()
            arguments?.get(key)?.let { data ->
                if (data is ArticleDetails) {
                    textArticleAuthor.text = data.author
                    textArticleContent.text = data.content


                    val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                    formatter.timeZone = TimeZone.getTimeZone("UTC")
                    var value: Date? = null
                    try {
                        value = formatter.parse(data.publishedAt.orEmpty())
                    } catch (e: ParseException) {
                        e.printStackTrace()
                    }
                    val dateFormatter = SimpleDateFormat("ddd-MMM-yyyy hh:mm aa")
                    dateFormatter.timeZone = TimeZone.getDefault()
                    textArticleDate.text = getString(R.string.text_published_date, dateFormatter.format(value))
                    textHeadlineTitle.text = data.title
                    textHeadlineCaption.text = data.description
                    imageHeadline.loadUrl(
                        data.urlToImage
                    )
                }
            }
        }
    }

    override fun initializeObservers() {

    }

    override fun initializeToBeRefresh(): () -> Unit {
        return noRefresh()
    }



}