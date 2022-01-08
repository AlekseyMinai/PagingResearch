package com.alexey.minay.paging_client.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.alexey.minay.paging_client.databinding.ActivityMainBinding
import com.alexey.minay.paging_client.presentation.NewsViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class NewsActivity : AppCompatActivity() {

    private val mViewModel by viewModel<NewsViewModel>()
    private val mDiffCallback = NewsDiff()
    private val mAdapter = NewsAdapter(mDiffCallback)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.initList()
        binding.initSwipeRefreshLayout()
        subscribeToViewModel()
    }

    private fun ActivityMainBinding.initList() {
        news.adapter = mAdapter.withLoadStateFooter(NewsLoadStateAdapter(
            onRetryClicked = {
                mAdapter.retry()
            }
        ))

        mAdapter.addLoadStateListener {
            swipeRefreshLayout.isRefreshing = false
        }
    }

    private fun ActivityMainBinding.initSwipeRefreshLayout() {
        swipeRefreshLayout.setOnRefreshListener {
            mAdapter.refresh()
        }
    }

    private fun subscribeToViewModel() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                mViewModel.flow.collectLatest {
                    mAdapter.submitData(it)
                }
            }
        }
    }

}