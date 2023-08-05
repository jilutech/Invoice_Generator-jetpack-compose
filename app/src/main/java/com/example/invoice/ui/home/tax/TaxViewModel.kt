package com.example.invoice.ui.home.tax

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.invoice.data.Resource
import com.example.invoice.data.home.models.TaxModel
import com.example.invoice.data.home.repo.TaxRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaxViewModel @Inject constructor(
    private val repository: TaxRepository
) : ViewModel(){

    private val _des = MutableStateFlow("")
    val des : MutableStateFlow<String> =_des

    private val _taxValue = MutableStateFlow("")
    val taxValue : MutableStateFlow<String> = _taxValue


    private val _isUpdating = MutableStateFlow<String?>(null)
    val isUpdating : MutableStateFlow<String?> =_isUpdating

    private val _areInputValidate = MutableStateFlow<Boolean>(false)
    val areInputValidate : MutableStateFlow<Boolean> = _areInputValidate


    private val _manageTax = MutableStateFlow<Resource<TaxModel>?>(null)
    val manageTax : MutableStateFlow<Resource<TaxModel>?> =_manageTax


    private val _taxList = MutableStateFlow<Resource<List<TaxModel>>?> (null)

    val taxList : MutableStateFlow<Resource<List<TaxModel>>?> =_taxList


    init {
        init()
    }


    private fun init() = viewModelScope.launch {
        getTaxes()
    }
    fun validateInputs() {
        val validate = des.value.trim().isNotEmpty() && taxValue.value.trim().isNotEmpty()
        _areInputValidate.value = validate
        /* @Todo more specific validations */
    }


    fun addTax() = viewModelScope.launch {
        _manageTax.value = Resource.Loading
        val tax = TaxModel(des.value, taxValue.value.toDouble())
        _manageTax.value = repository.addTax(tax)
        getTaxes()
    }
    fun updateTax() = viewModelScope.launch {
        _manageTax.value = Resource.Loading
        val tax = TaxModel(des.value, taxValue.value.toDouble()).also {
            it.id = _isUpdating.value ?: throw IllegalArgumentException("Business Id is null, you must call setUpdating() first")
        }
        _manageTax.value = repository.updateTax(tax)
        getTaxes()
    }
    fun deleteCustomer() = viewModelScope.launch {
        _isUpdating.value?.let {
            _manageTax.value = Resource.Loading
            repository.deleteTax(it)
            _manageTax.value = Resource.Success(TaxModel())
        }
        getTaxes()
    }
    fun setUpdating(tax: TaxModel?) {
        if (tax != null) {
            _isUpdating.value = tax.id
            des.value = tax.desc
            taxValue.value = tax.value.toString()
            validateInputs()
        } else {
            _isUpdating.value = null
            des.value = ""
            taxValue.value = ""
        }
    }

    fun resetResource() {
        _manageTax.value = null
    }

    private suspend fun getTaxes() {
        _taxList.value = Resource.Loading
        _taxList.value = repository.getTax()
    }
}