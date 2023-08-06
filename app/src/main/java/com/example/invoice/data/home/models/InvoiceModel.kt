package com.example.invoice.data.home.models

import com.example.invoice.data.home.repo.models.BaseModel
import com.example.invoice.data.home.repo.models.Customer

data class InvoiceModel(
    val business: Business? = null,
    val customer: Customer? = null,
    val tax: TaxModel? = null,
    val listOfItems: List<InvoiceItemModel> = listOf(),
    @field:JvmField
    var isPaid: Boolean = false,
    override var id: String = ""
) : BaseModel(id) {

    val invoiceAmount: Double
        get() = totalAmount + taxAmount

    val taxAmount: Double
        get() = totalAmount * (tax?.value ?: 1.0) / 100

    val totalAmount: Double
        get() = listOfItems.sumOf { it.qty * it.price }

    fun isPaid() = isPaid
}
