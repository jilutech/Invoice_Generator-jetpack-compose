package com.example.invoice.data.home.repo

import com.example.invoice.data.Resource
import com.example.invoice.data.home.models.InvoiceModel

interface InvoiceRepository {

    suspend fun getInvoices(): Resource<List<InvoiceModel>>

    suspend fun addInvoice(invoice: InvoiceModel): Resource<InvoiceModel>

    suspend fun updateInvoice(invoice: InvoiceModel): Resource<InvoiceModel>

    suspend fun deleteInvoice(id: String): Resource<Boolean>

    suspend fun updatePaidState(id: String, isPaid: Boolean): Resource<Boolean>
}