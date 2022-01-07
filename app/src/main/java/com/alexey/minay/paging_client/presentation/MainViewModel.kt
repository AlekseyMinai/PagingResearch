package com.alexey.minay.paging_client.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.alexey.minay.paging_client.data.MyPagingSource
import com.alexey.minay.paging_client.data.NewsGateway

class MainViewModel(
    private val gateway: NewsGateway
) : ViewModel() {

    val flow = Pager(
        PagingConfig(pageSize = 25)
    ) {
        MyPagingSource(gateway)
    }.flow
        .cachedIn(viewModelScope)

    class Factory(
        private val gateway: NewsGateway
    ) : ViewModelProvider.Factory {

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return MainViewModel(gateway) as T
        }

    }

}