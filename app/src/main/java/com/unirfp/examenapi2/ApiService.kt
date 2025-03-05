package com.unirfp.examenapi2

import com.unirfp.examenapi2.model.ProductosResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface ApiService {

    @GET("products")
    suspend fun getAllProductos(): Response<ProductosResponse>
}