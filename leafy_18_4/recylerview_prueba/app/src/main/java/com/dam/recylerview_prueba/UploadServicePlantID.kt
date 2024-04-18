package com.dam.recylerview_prueba

import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query


interface UploadServicePlantID {
    @Multipart
    @POST("/v2/identify/all?include-related-images=false&no-reject=false&lang=es&api-key=2b10BaJkdtVCtqIZwYqnQamhu")
    suspend fun uploadImage(
       @Part image: MultipartBody.Part,

    ): ResponseBody
}