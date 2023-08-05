package com.example.invoice.data.home.repo

import com.example.invoice.data.Resource
import com.example.invoice.data.home.BaseRepository
import com.example.invoice.data.home.models.TaxModel
import com.example.invoice.data.utils.await
import com.example.invoice.data.utils.currentDateTime
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.lang.Exception
import javax.inject.Inject

class TaxRepositoryImpl @Inject constructor(
    fireAuth: FirebaseAuth,
    firebaseFireStore: FirebaseFirestore
) : TaxRepository,BaseRepository<TaxModel>(fireAuth,firebaseFireStore,DB_TAXES)
{
    override suspend fun getTax(): Resource<List<TaxModel>> {
        return try {
            val snapshot =db.get().await()
            Resource.Success(getData(snapshot = snapshot,TaxModel::class.java))
        }catch (e : Exception){
            Resource.Failure(e)
        }
    }

    override suspend fun addTax(taxModel: TaxModel): Resource<TaxModel> {
            return try {
                db.add(taxModel).await()
                Resource.Success(taxModel)
            }catch (e : Exception){
                Resource.Failure(e)
            }
    }

    override suspend fun updateTax(taxModel: TaxModel): Resource<TaxModel> {
        return try {
            taxModel.updatedAt = currentDateTime
            db.document(taxModel.id).set(taxModel).await()
            Resource.Success(taxModel)
        }catch (e : Exception){
            Resource.Failure(e)
        }
    }

    override suspend fun deleteTax(id: String): Resource<Boolean> {
       return try {
           db.document(id).delete().await()
           Resource.Success(true)
       }catch (e : Exception){
           Resource.Failure(e)
       }
    }
    companion object {
        private const val DB_TAXES = "taxes"
    }
}

