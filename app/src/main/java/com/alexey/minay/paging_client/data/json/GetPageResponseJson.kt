package com.alexey.minay.paging_client.data.json

import com.alexey.minay.paging_client.domain.News
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetPageResponseJson(
    @SerialName("news")
    val news: List<NewsJson>,
    @SerialName("nextPage")
    val nextPage: Int? = null
) {

    fun getDomainNews() = news.map { it.asNews() }

}

@Serializable
data class NewsJson(
    @SerialName("title")
    val title: String,
    @SerialName("thumbnail")
    val thumbnailUrl: String
) {

    fun asNews() = News(
        title = title,
        thumbnailUrl = thumbnailUrl
    )

}