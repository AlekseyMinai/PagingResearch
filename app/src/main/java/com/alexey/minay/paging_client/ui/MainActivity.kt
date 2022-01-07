package com.alexey.minay.paging_client.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.alexey.minay.paging_client.R
import com.alexey.minay.paging_client.data.NewsGateway
import com.alexey.minay.paging_client.databinding.ActivityMainBinding
import com.alexey.minay.paging_client.presentation.MainViewModel
import io.ktor.client.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json

class MainActivity : AppCompatActivity() {

    private val mViewModel by viewModels<MainViewModel> {
        val client = HttpClient {
            install(JsonFeature) {
                serializer = KotlinxSerializer(kotlinx.serialization.json.Json {
                    prettyPrint = true
                    isLenient = true
                    ignoreUnknownKeys = true
                })
            }
        }

        MainViewModel.Factory(NewsGateway(client))
    }
    private val mDiffCallback = NewsDiff()
    private val mAdapter = NewsAdapter(mDiffCallback)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.news.adapter = mAdapter.withLoadStateFooter(NewsLoadStateAdapter())

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                mViewModel.flow.collectLatest {
                    mAdapter.submitData(it)
                }
            }
        }
    }
}