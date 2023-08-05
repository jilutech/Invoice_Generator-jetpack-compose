package com.example.invoice.ui.home.bussiness

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.invoice.data.Resource
import com.example.invoice.data.home.models.Business
import com.example.invoice.data.home.repo.MyBusinessRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException
import javax.inject.Inject

@HiltViewModel
class BusinessViewModel @Inject constructor(
    private val repository: MyBusinessRepository
) : ViewModel(){

    val name = MutableStateFlow("")
    val address = MutableStateFlow("")
    val phone = MutableStateFlow("")
    val email = MutableStateFlow("")


    private val _isUpdate = MutableStateFlow<String?>(null)
    val isUpdate : MutableStateFlow<String?> =_isUpdate

    private val _areValidated = MutableStateFlow<Boolean>(true)
    val areValidated : MutableStateFlow<Boolean> = _areValidated


    private val _getMyBusiness = MutableStateFlow<Resource<List<Business>>?>(null)
    val getMyBusiness : MutableStateFlow<Resource<List<Business>>?> =_getMyBusiness

    private val _manageMyBusiness = MutableStateFlow<Resource<Business>?>(null)
    val manageMyBusiness : MutableStateFlow<Resource<Business>?> = _manageMyBusiness

    private val _canAddBusiness = MutableStateFlow(true)
    val canAddBusiness  = _canAddBusiness


    init {
        getMyBusiness()
    }

    fun validatesInput(){
        _areValidated.value = name.value.trim().isNotEmpty()&&
                              address.value.trim().isNotEmpty()&&
                              phone.value.trim().isNotEmpty()&&
                              address.value.trim().isNotEmpty()
    }

    fun addMyBusinessHolder()=viewModelScope.launch {
        _manageMyBusiness.value = Resource.Loading
        val business = Business(name.value,address.value, phone.value, email.value)
        _manageMyBusiness.value = repository.addMyBusinessHolder(business)
        getMyBusiness()
    }
    fun updateMyBusinessHolder() = viewModelScope.launch {
        _manageMyBusiness.value = Resource.Loading
        val business = Business(name.value,address.value, phone.value, email.value).also {
            it.id = _isUpdate.value ?: throw IllegalArgumentException("Business Id is null, you must call setUpdating() first")
        }
        _manageMyBusiness.value = repository.updateMyBusiness(business)
        getMyBusiness()
    }

    fun deleteMyBusiness() = viewModelScope.launch {
        _isUpdate.value?.let {
        _manageMyBusiness.value = Resource.Loading
        repository.deleteMyBusinessHolder(it)
            _manageMyBusiness.value  = Resource.Success(Business())
        }
        getMyBusiness()
    }
    fun getMyBusiness() = viewModelScope.launch {
        _getMyBusiness.value = Resource.Loading
        _getMyBusiness.value = repository.getMyBusinessHolders()
    }


    fun setUpdating(business: Business?) {
        if (business != null) {
            _isUpdate.value = business.id
            name.value = business.name
            address.value = business.address
            phone.value = business.phone
            email.value = business.email
            validatesInput()
        } else {
            isUpdate.value = null
            name.value = ""
            address.value = ""
            phone.value = ""
            email.value = ""
        }
    }

    private suspend fun canAddBusiness() {
        _canAddBusiness.value = repository.canAddMyBusiness()
    }

    fun resetResource() {
        manageMyBusiness.value = null
    }





}