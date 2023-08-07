package com.example.invoice.data.home.repo

import com.example.invoice.data.Resource
import com.example.invoice.data.home.models.Dashboard
import com.example.invoice.data.home.models.InvoiceModel
import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject

class DashboardRepositoryImpl @Inject constructor(
    private val invoiceRepository: InvoiceRepository,
    private val auth: FirebaseAuth
) : DashboardRepository {

    override suspend fun getDashboardInfo(): Resource<Dashboard> {
        val invoices = invoiceRepository.getInvoices()
        return try {
            Resource.Success(getDashboardInfo((invoices as Resource.Success).result))
        } catch (e: Exception) {
            Resource.Failure(e)
        }
    }

    private fun getDashboardInfo(invoices: List<InvoiceModel>) = Dashboard(
        invoiceCount = invoices.size,
        receivedAmount = invoices.filter { it.isPaid() }.sumOf { it.invoiceAmount },
        totalAmount = invoices.sumOf { it.invoiceAmount },
        pendingInvoices = invoices.count { !it.isPaid() },
        pendingAmount = invoices.filter { !it.isPaid() }.sumOf { it.invoiceAmount },
    )
}