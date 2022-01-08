package com.alexey.minay.paging_client.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.alexey.minay.paging_client.domain.News
import kotlinx.coroutines.flow.Flow

class NewsViewModel(
    pagerFlow: Flow<PagingData<News>>
) : ViewModel() {

    val flow = pagerFlow
        .cachedIn(viewModelScope)

}