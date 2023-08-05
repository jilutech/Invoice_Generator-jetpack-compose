package com.example.invoice.data.home.repo

import com.example.invoice.data.Resource
import com.example.invoice.data.home.models.Business

interface MyBusinessRepository {

    suspend fun getMyBusinessHolders() : Resource<List<Business>>

    suspend fun addMyBusinessHolder(business: Business) : Resource<Business>

    suspend fun updateMyBusiness (business: Business) : Resource<Business>

    suspend fun canAddMyBusiness() : Boolean

    suspend fun deleteMyBusinessHolder (id : String) : Resource<Boolean>
}