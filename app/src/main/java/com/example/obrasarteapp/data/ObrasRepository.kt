package com.example.obrasarteapp.data

import com.example.obrasarteapp.data.remote.ObrasApi
import com.example.obrasarteapp.data.remote.model.ObraDto
import retrofit2.Retrofit

class ObrasRepository(retrofit: Retrofit) {

    private val api = retrofit.create(ObrasApi::class.java)

    suspend fun getObras(): List<ObraDto> {
        return api.getObras()
    }
}

