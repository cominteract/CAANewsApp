package com.ainsigne.home.ui.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.ainsigne.common.utils.extension.loadUrl
import com.ainsigne.common.utils.extension.setOnSingleClickListener
import com.ainsigne.domain.entities.ArticleDomainEntities
import com.ainsigne.home.databinding.ItemArticleBinding


class HeadlineViewHolder(
    private val binding: ItemArticleBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(data: ArticleDomainEntities.Article, articleBlock: ((ArticleDomainEntities.Article) -> Unit)) {
        binding.apply {
            textHeadlineTitle.text = data.title
            textHeadlineCaption.text = data.description
            imageHeadline.loadUrl(
                data.urlToImage
            )
            root.setOnSingleClickListener {
                articleBlock.invoke(data)
            }
        }
    }
}
