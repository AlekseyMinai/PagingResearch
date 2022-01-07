package com.alexey.minay.paging_client.data

import android.util.Log
import com.alexey.minay.paging_client.data.json.GetPageResponseJson
import io.ktor.client.*
import io.ktor.client.request.*
import kotlinx.coroutines.delay
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class NewsGateway(
    private val httpClient: HttpClient
) {

    suspend fun getNews(page: Int) =
        wrapRequest<String> {
            //bad connection
            delay(400)

            Log.d("NewsGateway", "Load page $page")

            httpClient.get(
                "https://raw.githubusercontent.com/AlekseyMinai/" +
                        "PagingResearch/main/fake/$page.json"
            )
        }.map {
            Json.decodeFromString<GetPageResponseJson>(this)
        }

}