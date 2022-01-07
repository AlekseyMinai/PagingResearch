package com.alexey.minay.paging_client.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.alexey.minay.paging_client.databinding.ItemErrorBinding
import com.alexey.minay.paging_client.databinding.ItemProgressBinding

class NewsLoadStateAdapter(
    private val onRetryClicked: () -> Unit
) : LoadStateAdapter<NewsLoadStateAdapter.ItemViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): ItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (loadState) {
            is LoadState.NotLoading -> error("Error")
            is LoadState.Error -> ErrorViewHolder(
                ItemErrorBinding.inflate(inflater, parent, false), onRetryClicked
            )
            LoadState.Loading ->
                ProgressViewHolder(ItemProgressBinding.inflate(inflater, parent, false))
        }
    }

    override fun onBindViewHolder(holder: ItemViewHolder, loadState: LoadState) {
        holder.bind()
    }

    abstract inner class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        open fun bind() = Unit
    }

    inner class ProgressViewHolder(
        binding: ItemProgressBinding
    ) : ItemViewHolder(binding.root)

    inner class ErrorViewHolder(
        private val binding: ItemErrorBinding,
        private val onRetryClicked: () -> Unit
    ) : ItemViewHolder(binding.root) {

        override fun bind() {
            binding.retry.setOnClickListener { onRetryClicked() }
        }

    }


}