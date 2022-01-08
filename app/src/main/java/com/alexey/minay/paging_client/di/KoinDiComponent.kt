package com.alexey.minay.paging_client.di

import android.content.Context
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.alexey.minay.paging_client.data.NewsGateway
import com.alexey.minay.paging_client.data.NewsPagingSource
import com.alexey.minay.paging_client.domain.News
import com.alexey.minay.paging_client.presentation.NewsViewModel
import io.ktor.client.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

object KoinDiComponent {

    private val mAppModule = module {
        single {
            HttpClient {
                install(JsonFeature) {
                    serializer = KotlinxSerializer(kotlinx.serialization.json.Json {
                        prettyPrint = true
                        isLenient = true
                        ignoreUnknownKeys = true
                    })
                }
            }
        }
        factory { NewsGateway(get()) }
        factory { NewsPagingSource(get()) }
        factory {
            Pager(
                config = PagingConfig(pageSize = 25),
                pagingSourceFactory = { get<NewsPagingSource>() }
            )
        }

        viewModel { NewsViewModel(get<Pager<Int, News>>().flow) }
    }

    fun init(context: Context) {
        startKoin {
            androidContext(context)
            modules(mAppModule)
        }
    }

}