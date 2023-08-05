package com.example.invoice.data.home.repo

import com.example.invoice.data.Resource
import com.example.invoice.data.home.models.TaxModel

interface TaxRepository {

    suspend fun getTax() : Resource<List<TaxModel>>

    suspend fun addTax(taxModel: TaxModel) : Resource<TaxModel>

    suspend fun updateTax(taxModel: TaxModel) : Resource<TaxModel>

    suspend fun deleteTax(id : String) : Resource<Boolean>

}