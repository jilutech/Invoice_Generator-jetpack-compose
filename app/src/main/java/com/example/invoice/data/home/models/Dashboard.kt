package com.example.invoice.data.home.models

import com.example.invoice.data.home.repo.models.BaseModel

data class Dashboard(
    val invoiceCount: Int,
    val receivedAmount: Double,
    val totalAmount: Double,
    val pendingInvoices: Int,
    val pendingAmount: Double
): BaseModel()