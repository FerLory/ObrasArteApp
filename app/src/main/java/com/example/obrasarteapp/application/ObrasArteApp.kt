

package com.example.obrasarteapp.application

import android.app.Application
import com.example.obrasarteapp.data.ObrasRepository
import com.example.obrasarteapp.data.remote.RetrofitHelper

class ObrasArteApp : Application() {
    private val retrofit by lazy {
        RetrofitHelper.getRetrofit()
    }

    val repository by lazy {
        ObrasRepository(retrofit)
    }
}

