package com.example.obrasarteapp.data.remote.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ObraDto(
    val id: Int,
    val nombre: String,
    val autor: String,
    @SerializedName("año")
    val anio: Int,
    val estilo: String,
    @SerializedName("ubicación")
    val ubicacion: String,
    val imagen: String,
    @SerializedName("video_url")
    val videoUrl: String
) : Parcelable