package com.ainsigne.home.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ainsigne.domain.entities.ArticleDomainEntities
import com.ainsigne.home.R
import com.ainsigne.home.databinding.ItemArticleBinding
import com.ainsigne.home.ui.viewholder.HeadlineViewHolder

class HeadlineAdapter(
    private val block: ((ArticleDomainEntities.Article) -> Unit)
) :
    RecyclerView.Adapter<HeadlineViewHolder>() {

    private var articles: List<ArticleDomainEntities.Article> = emptyList()

    fun updateList(articles: List<ArticleDomainEntities.Article>) {
        this.articles = articles
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeadlineViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(
                R.layout.item_article,
                parent,
                false
            )
        return HeadlineViewHolder(
            ItemArticleBinding.bind(view)
        )
    }

    override fun getItemCount(): Int = articles.size

    override fun onBindViewHolder(holder: HeadlineViewHolder, position: Int) {
        holder.bind(
            data = articles.get(position),
            articleBlock = block
        )
    }
}
