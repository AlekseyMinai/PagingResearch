package com.alexey.minay.paging_client.data

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
            delay(5000)
            httpClient.get("http://10.0.2.2:8080/pages/$page")
        }.map {
            Json.decodeFromString<GetPageResponseJson>(this)
        }

}