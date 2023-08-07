package com.example.invoice.data.home.repo

import com.example.invoice.data.Resource
import com.example.invoice.data.home.models.BusinessModel

interface MyBusinessRepository {

    suspend fun getMyBusinessHolders() : Resource<List<BusinessModel>>

    suspend fun addMyBusinessHolder(businessModel: BusinessModel) : Resource<BusinessModel>

    suspend fun updateMyBusiness (businessModel: BusinessModel) : Resource<BusinessModel>

    suspend fun canAddMyBusiness() : Boolean

    suspend fun deleteMyBusinessHolder (id : String) : Resource<Boolean>
}