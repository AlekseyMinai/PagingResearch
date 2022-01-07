package com.alexey.minay.paging_client.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.alexey.minay.paging_client.R
import com.alexey.minay.paging_client.databinding.ItemNewsBinding
import com.alexey.minay.paging_client.domain.News
import com.bumptech.glide.Glide

class NewsAdapter(
    diffCallback: DiffUtil.ItemCallback<News>
) : PagingDataAdapter<News, NewsAdapter.NewsViewHolder>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return NewsViewHolder(ItemNewsBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val item = getItem(position)
        item?.let { holder.bind(it) }
    }

    inner class NewsViewHolder(
        private val binding: ItemNewsBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(news: News) = with(binding) {
            Glide.with(itemView)
                .load(news.thumbnailUrl)
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder)
                .centerCrop()
                .into(image)

            title.text = news.title
        }

    }
}

class NewsDiff : DiffUtil.ItemCallback<News>() {

    override fun areItemsTheSame(oldItem: News, newItem: News): Boolean {
        return oldItem != newItem
    }

    override fun areContentsTheSame(oldItem: News, newItem: News): Boolean {
        return oldItem != newItem
    }

}