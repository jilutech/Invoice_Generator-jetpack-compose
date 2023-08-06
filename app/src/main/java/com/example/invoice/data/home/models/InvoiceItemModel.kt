package com.example.invoice.data.home.models

import com.example.invoice.data.home.repo.models.BaseModel

data class InvoiceItemModel(
    val desc: String = "",
    val qty: Double = 0.0,
    val price: Double = 0.0
) : BaseModel() {

    val amount: Double
        get() = qty * price
}