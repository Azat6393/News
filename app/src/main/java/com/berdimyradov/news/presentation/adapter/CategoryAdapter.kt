package com.berdimyradov.news.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.berdimyradov.news.databinding.ItemCategoryBinding
import com.berdimyradov.news.domain.model.Category

class CategoryAdapter : ListAdapter<Category, CategoryAdapter.CategoryViewHolder>(DiffCallBack) {

    private var context: Context? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        context = parent.context
        return CategoryViewHolder(
            ItemCategoryBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null) {
            holder.bind(item)
        }
    }

    inner class CategoryViewHolder(private val binding: ItemCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        val mAdapter: ArticleAdapter by lazy { ArticleAdapter() }

        fun bind(item: Category) {
            binding.categoryTv.text = item.category.replaceFirstChar(Char::titlecase)
            binding.articleTv.apply {
                adapter = mAdapter
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                setHasFixedSize(true)
            }
            mAdapter.submitList(item.articles)
        }
    }

    companion object {
        private val DiffCallBack = object : DiffUtil.ItemCallback<Category>() {
            override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean {
                return oldItem == newItem
            }
        }
    }
}