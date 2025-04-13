
package com.example.obrasarteapp.data.remote

import com.example.obrasarteapp.data.remote.model.ObraDto
import retrofit2.http.GET

interface ObrasApi {
    @GET("obras")
    suspend fun getObras(): List<ObraDto>
}