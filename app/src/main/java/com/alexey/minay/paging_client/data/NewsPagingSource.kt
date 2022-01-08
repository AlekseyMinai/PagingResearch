package com.alexey.minay.paging_client.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.alexey.minay.paging_client.domain.News

class NewsPagingSource(
    private val gateway: NewsGateway
) : PagingSource<Int, News>() {

    override fun getRefreshKey(state: PagingState<Int, News>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val page = state.closestPageToPosition(anchorPosition)
        return page?.prevKey?.plus(1) ?: page?.nextKey?.minus(1)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, News> {
        val page = params.key ?: 1

        return when (val result = gateway.getNews(page)) {
            is Result.Success ->
                LoadResult.Page(result.data.getDomainNews(), null, result.data.nextPage)
            is Result.Error ->
                // FIXME: 07.01.2022 Подумать как сделать лучше
                LoadResult.Error(RuntimeException())
        }
    }

}