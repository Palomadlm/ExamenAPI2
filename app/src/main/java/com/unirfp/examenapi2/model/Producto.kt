package com.unirfp.examenapi2.model

data class Producto(
    val _id: String,
    val name: String,
    val description: String,
    val price: Double,
    val category: String,
    val image: String,
    val active: Boolean,
)