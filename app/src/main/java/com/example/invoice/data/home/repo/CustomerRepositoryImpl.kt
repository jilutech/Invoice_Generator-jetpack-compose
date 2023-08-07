package com.example.invoice.data.home.repo

import com.example.invoice.data.Resource
import com.example.invoice.data.home.repo.models.CustomerModel
import com.example.invoice.data.home.repo.models.CustomerRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject
import com.example.invoice.data.home.BaseRepository
import com.example.invoice.data.utils.await
import com.example.invoice.data.utils.currentDateTime
import java.lang.Exception

class CustomerRepositoryImpl @Inject constructor(
    var fireAuth :FirebaseAuth,
    var firebaseFireStore : FirebaseFirestore
) : CustomerRepository,BaseRepository<CustomerModel>(fireAuth,firebaseFireStore, DB_CUSTOMERS) {
    override suspend fun getCustomers(): Resource<List<CustomerModel>> {
        return try {
            val snapshot =db.get().await()
            Resource.Success(getData(snapshot = snapshot,CustomerModel::class.java))
        }catch (e:Exception){
            Resource.Failure(e)
        }

    }

    override suspend fun addAddress(customer: CustomerModel): Resource<CustomerModel> {
        return try {
          db.add(customer).await()
            Resource.Success(customer)
        }catch (e : Exception){
            Resource.Failure(e)
        }
    }

    override suspend fun updateCustomer(customer: CustomerModel): Resource<CustomerModel> {
        return try {
            customer.updatedAt = currentDateTime
            db.document(customer.id).set(customer).await()
            Resource.Success(customer)

        }catch (e : Exception){
            Resource.Failure(e)
        }
    }

    override suspend fun deleteCustomer(id: String): Resource<Boolean> {
        return try {
            db.document(id).delete().await()
            Resource.Success(true)
        } catch (e: Exception) {
            Resource.Failure(e)
        }
    }

    companion object {
        private const val DB_CUSTOMERS = "customers"
    }
}