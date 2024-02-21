package com.berdimyradov.news.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.berdimyradov.news.R
import com.berdimyradov.news.databinding.ItemArticleBinding
import com.berdimyradov.news.domain.model.Article

class ArticleAdapter : ListAdapter<Article, ArticleAdapter.ArticleViewHolder>(DiffCallBack) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        return ArticleViewHolder(
            ItemArticleBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null) {
            holder.bind(item)
        }
    }

    inner class ArticleViewHolder(private val binding: ItemArticleBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val item = getItem(position)
                    if (item != null) {

                    }
                }
            }
        }

        fun bind(item: Article) {
            binding.apply {
                articleTitleTv.text = item.title
                articleImageIv.load(item.imageUrl) {
                    crossfade(false)
                    placeholder(R.drawable.baseline_broken_image_24)
                }
            }
        }
    }

    companion object {
        private val DiffCallBack = object : DiffUtil.ItemCallback<Article>() {
            override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
                return oldItem.title == newItem.title
            }

            override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
                return oldItem == newItem
            }
        }
    }
}