package com.example.invoice.data.home.repo.models

import com.example.invoice.data.Resource

interface CustomerRepository {

    suspend fun getCustomers() : Resource<List<CustomerModel>>
    suspend fun addAddress(customer: CustomerModel) : Resource<CustomerModel>
    suspend fun updateCustomer(customer: CustomerModel) : Resource<CustomerModel>
    suspend fun deleteCustomer(id : String) : Resource<Boolean>




}