package com.alexey.minay.paging_client.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.alexey.minay.paging_client.databinding.ItemErrorBinding
import com.alexey.minay.paging_client.databinding.ItemProgressBinding

class NewsLoadStateAdapter : LoadStateAdapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (loadState) {
            is LoadState.NotLoading -> error("Error")
            is LoadState.Error ->
                ErrorViewHolder(ItemErrorBinding.inflate(inflater, parent, false))
            LoadState.Loading ->
                ProgressViewHolder(ItemProgressBinding.inflate(inflater, parent, false))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, loadState: LoadState) = Unit

    inner class ProgressViewHolder(
        binding: ItemProgressBinding
    ) : RecyclerView.ViewHolder(binding.root)

    inner class ErrorViewHolder(
        binding: ItemErrorBinding
    ) : RecyclerView.ViewHolder(binding.root)


}