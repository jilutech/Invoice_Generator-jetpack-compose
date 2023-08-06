package com.example.invoice.data.home.repo

import com.example.invoice.data.Resource
import com.example.invoice.data.home.BaseRepository
import com.example.invoice.data.home.models.InvoiceModel
import com.example.invoice.data.utils.await
import com.example.invoice.data.utils.currentDateTime
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject

class InvoiceRepositoryImpl @Inject constructor(
    auth: FirebaseAuth,
    firestore: FirebaseFirestore
) : InvoiceRepository, BaseRepository<InvoiceModel>(auth, firestore, DB_INVOICES) {

    override suspend fun getInvoices(): Resource<List<InvoiceModel>> {
        return try {
            val snapshot = db.get().await()
            Resource.Success(getData(snapshot, InvoiceModel::class.java))
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Failure(e)
        }
    }

    override suspend fun addInvoice(invoice: InvoiceModel): Resource<InvoiceModel> {
        return try {
            db.add(invoice).await()
            Resource.Success(invoice)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Failure(e)
        }
    }

    override suspend fun updateInvoice(invoice: InvoiceModel): Resource<InvoiceModel> {
        return try {
            invoice.updatedAt = currentDateTime
            db.document(invoice.id).set(invoice).await()
            Resource.Success(invoice)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Failure(e)
        }
    }

    override suspend fun deleteInvoice(id: String): Resource<Boolean> {
        return try {
            db.document(id).delete().await()
            Resource.Success(true)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Failure(e)
        }
    }

    override suspend fun updatePaidState(id: String, isPaid: Boolean): Resource<Boolean> {
        return try {
            db.document(id).update(FIELD_IS_PAID, isPaid).await()
            Resource.Success(true)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Failure(e)
        }
    }

    companion object {
        private const val DB_INVOICES = "invoices"
        private const val FIELD_IS_PAID = "isPaid"
    }
}