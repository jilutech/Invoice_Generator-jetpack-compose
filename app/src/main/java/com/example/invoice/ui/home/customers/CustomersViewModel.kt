package com.example.invoice.ui.home.customers

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.invoice.data.Resource
import com.example.invoice.data.home.repo.models.Customer
import com.example.invoice.data.home.repo.models.CustomerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException
import javax.inject.Inject

@HiltViewModel
class CustomersViewModel @Inject constructor(
    private var customerRepository: CustomerRepository
):ViewModel() {

    var name = MutableStateFlow("")
    var address = MutableStateFlow("")
    var phone = MutableStateFlow("")
    var email = MutableStateFlow("")


    private val _isUpdating = MutableStateFlow<String?>(null)
    val isUpdating : StateFlow<String?> = _isUpdating

    private val _areInputValid = MutableStateFlow(false)
    val areInputValid : StateFlow<Boolean> = _areInputValid


    private val _manageCustomerResult = MutableStateFlow<Resource<Customer>?>(null)
    val manageCustomerResult: StateFlow<Resource<Customer>?> = _manageCustomerResult

    private val _customers = MutableStateFlow<Resource<List<Customer>>?>(null)
    val customer : StateFlow<Resource<List<Customer>>?> = _customers
    init {
        init()
    }


    private fun init() = viewModelScope.launch {
        getCustomers()

    }

    private suspend fun getCustomers(){
        _customers.value = Resource.Loading
        _customers.value = customerRepository.getCustomers()
    }

    @SuppressLint("SuspiciousIndentation")
    fun validateInputs(){
        _areInputValid.value =
              name.value.trim().isNotEmpty() && address.value.trim().isNotEmpty() && phone.value.trim().isNotEmpty()
              email.value.trim().isNotEmpty()
    }

    fun addCustomer() = viewModelScope.launch {

        _manageCustomerResult.value = Resource.Loading
        val customer = Customer(name.value,address.value, phone.value,email.value)
        _manageCustomerResult.value = customerRepository.addAddress(customer)
        getCustomers()

    }

    fun updateCustomer() = viewModelScope.launch {
        _manageCustomerResult.value = Resource.Loading
        val custmomer = Customer(name.value,address.value,phone.value,email.value).also {
            it.id = _isUpdating.value ?: throw IllegalArgumentException("Business Id is null, you must call setUpdating() first")
        }
        _manageCustomerResult.value = customerRepository.updateCustomer(custmomer)
    }

    fun delete()  = viewModelScope.launch {
        _isUpdating.value?.let {
            _manageCustomerResult.value = Resource.Loading
            customerRepository.deleteCustomer(it)
            _manageCustomerResult.value = Resource.Success(Customer())
        }
        getCustomers()
    }

    fun setUpdating(customer: Customer?) {
        if (customer != null) {
            _isUpdating.value = customer.id
            name.value = customer.name
            address.value = customer.address
            phone.value = customer.phone
            email.value = customer.mailId
            validateInputs()
        } else {
            _isUpdating.value = null
            name.value = ""
            address.value = ""
            phone.value = ""
            email.value = ""
        }
    }

    fun resetResource() {
        _manageCustomerResult.value = null
    }





}