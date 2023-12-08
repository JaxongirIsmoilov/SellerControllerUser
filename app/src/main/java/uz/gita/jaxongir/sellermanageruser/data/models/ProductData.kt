package uz.gita.jaxongir.sellermanageruser.data.models

import com.google.firebase.database.DataSnapshot

data class ProductData(
    val id: String,
    val name: String,
    var count: Int,
    val initialPrice: Double,
    val soldPrice: Double,
    val isValid: Boolean,
    val comment: String,
    val sellerName: String,
)

fun DataSnapshot.toProductData(): ProductData = ProductData(
    id = child("id").getValue(String::class.java) ?: "",
    name = child("name").getValue(String::class.java) ?: "",
    count = child("count").getValue(Int::class.java) ?: 0,
    initialPrice = child("initialPrice").getValue(Double::class.java) ?: 0.0,
    soldPrice = child("soldPrice").getValue(Double::class.java) ?: 0.0,
    isValid = child("isValid").getValue(Boolean::class.java) ?: true,
    comment = child("comment").getValue(String::class.java) ?: "",
    sellerName = child("sellerName").getValue(String::class.java) ?: ""
)
