package com.example.invoice.data.home.repo

import com.example.invoice.data.Resource
import com.example.invoice.data.home.BaseRepository
import com.example.invoice.data.home.models.BusinessModel
import com.example.invoice.data.utils.await
import com.example.invoice.data.utils.currentDateTime
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject

class MyBusinessRepositoryImpl @Inject constructor(
    auth: FirebaseAuth,
    firestore: FirebaseFirestore
) : MyBusinessRepository,BaseRepository<BusinessModel>(auth, firestore , DB_MY_BUSINESSES)  {
    override suspend fun getMyBusinessHolders(): Resource<List<BusinessModel>> {
        return try {
            val snapshot = db.get().await()
            Resource.Success(getData(snapshot,BusinessModel::class.java))
        }catch (e: Exception) {
            Resource.Failure(e)
        }
    }

    override suspend fun addMyBusinessHolder(businessModel: BusinessModel): Resource<BusinessModel> {
        return try {
            db.add(businessModel).await()
            Resource.Success(businessModel)
        } catch (e: Exception) {
            Resource.Failure(e)
        }
    }

    override suspend fun updateMyBusiness(businessModel: BusinessModel): Resource<BusinessModel> {
        return try {
            businessModel.updatedAt = currentDateTime
            db.document(businessModel.id).set(businessModel).await()
            Resource.Success(businessModel)
        } catch (e: Exception) {
            Resource.Failure(e)
        }    }

    override suspend fun canAddMyBusiness(): Boolean {
        return db.get().await().size() < MAX_ALLOWED_BUSINESS
    }

    override suspend fun deleteMyBusinessHolder(id: String): Resource<Boolean> {
        return try {
            db.document(id).delete().await()
            Resource.Success(true)
        } catch (e: Exception) {
            Resource.Failure(e)
        }    }
    companion object {
        private const val DB_MY_BUSINESSES = "my_businesses"
        private const val MAX_ALLOWED_BUSINESS = 5
    }
}