package com.example.invoice.ui.home.bussiness

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.invoice.data.Resource
import com.example.invoice.data.home.models.BusinessModel
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


    private val _getMyBusinessModel = MutableStateFlow<Resource<List<BusinessModel>>?>(null)
    val getMyBusinessModel : MutableStateFlow<Resource<List<BusinessModel>>?> =_getMyBusinessModel

    private val _manageMyBusinessModel = MutableStateFlow<Resource<BusinessModel>?>(null)
    val manageMyBusinessModel : MutableStateFlow<Resource<BusinessModel>?> = _manageMyBusinessModel

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
        _manageMyBusinessModel.value = Resource.Loading
        val businessModel = BusinessModel(name.value,address.value, phone.value, email.value)
        _manageMyBusinessModel.value = repository.addMyBusinessHolder(businessModel)
        getMyBusiness()
    }
    fun updateMyBusinessHolder() = viewModelScope.launch {
        _manageMyBusinessModel.value = Resource.Loading
        val businessModel = BusinessModel(name.value,address.value, phone.value, email.value).also {
            it.id = _isUpdate.value ?: throw IllegalArgumentException("BusinessModel Id is null, you must call setUpdating() first")
        }
        _manageMyBusinessModel.value = repository.updateMyBusiness(businessModel)
        getMyBusiness()
    }

    fun deleteMyBusiness() = viewModelScope.launch {
        _isUpdate.value?.let {
        _manageMyBusinessModel.value = Resource.Loading
        repository.deleteMyBusinessHolder(it)
            _manageMyBusinessModel.value  = Resource.Success(BusinessModel())
        }
        getMyBusiness()
    }
    fun getMyBusiness() = viewModelScope.launch {
        _getMyBusinessModel.value = Resource.Loading
        _getMyBusinessModel.value = repository.getMyBusinessHolders()
    }


    fun setUpdating(businessModel: BusinessModel?) {
        if (businessModel != null) {
            _isUpdate.value = businessModel.id
            name.value = businessModel.name
            address.value = businessModel.address
            phone.value = businessModel.phone
            email.value = businessModel.email
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
        manageMyBusinessModel.value = null
    }





}