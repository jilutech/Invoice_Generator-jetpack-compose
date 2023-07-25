package com.example.invoice.data.home.repo.models

import com.example.invoice.data.Resource

interface CustomerRepository {

    suspend fun getCustomers() : Resource<List<Customer>>
    suspend fun addAddress(customer: Customer) : Resource<Customer>
    suspend fun updateCustomer(customer: Customer) : Resource<Customer>
    suspend fun deleteCustomer(id : String) : Resource<Boolean>




}