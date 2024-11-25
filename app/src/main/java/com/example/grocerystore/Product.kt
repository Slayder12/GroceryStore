package com.example.grocerystore

import android.graphics.Bitmap
import java.io.Serializable

class Product(
    val productName: String?,
    val price: String?,
    val description: String?,
    val image: String?
) : Serializable
