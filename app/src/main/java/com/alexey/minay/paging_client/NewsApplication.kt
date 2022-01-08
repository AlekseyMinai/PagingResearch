package com.alexey.minay.paging_client

import android.app.Application
import com.alexey.minay.paging_client.di.KoinDiComponent

class NewsApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        KoinDiComponent.init(this)
    }

}