package com.example.invoice.ui.home.invoice

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.invoice.data.Resource
import com.example.invoice.data.home.models.BusinessModel
import com.example.invoice.data.home.models.InvoiceItemModel
import com.example.invoice.data.home.models.InvoiceModel
import com.example.invoice.data.home.models.TaxModel
import com.example.invoice.data.home.repo.InvoiceRepository
import com.example.invoice.data.home.repo.MyBusinessRepository
import com.example.invoice.data.home.repo.TaxRepository
import com.example.invoice.data.home.repo.models.CustomerModel
import com.example.invoice.data.home.repo.models.CustomerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InvoicesViewModel @Inject constructor(
    private val invoiceRepository: InvoiceRepository,
    private val businessRepository: MyBusinessRepository,
    private val customersRepository: CustomerRepository,
    private val taxRepository: TaxRepository
) : ViewModel() {

    val desc = MutableStateFlow("")
    val qty = MutableStateFlow("")
    val price = MutableStateFlow("")

    private val _businesses = MutableStateFlow<Resource<List<BusinessModel>>?>(null)
    val businesses: StateFlow<Resource<List<BusinessModel>>?> = _businesses

    private val _customers = MutableStateFlow<Resource<List<CustomerModel>>?>(null)
    val customers: StateFlow<Resource<List<CustomerModel>>?> = _customers

    private val _taxes = MutableStateFlow<Resource<List<TaxModel>>?>(null)
    val taxes: StateFlow<Resource<List<TaxModel>>?> = _taxes

    private val _invoices = MutableStateFlow<Resource<List<InvoiceModel>>?>(null)
    val invoices: StateFlow<Resource<List<InvoiceModel>>?> = _invoices

    private val _invoice = MutableStateFlow(InvoiceModel())
    val invoice: StateFlow<InvoiceModel> = _invoice

    private val _createInvoice = MutableStateFlow<Resource<InvoiceModel>?>(null)
    val createInvoice: StateFlow<Resource<InvoiceModel>?> = _createInvoice

    private val _areInputsValid = MutableStateFlow(false)
    val areInputsValid: StateFlow<Boolean> = _areInputsValid

    private val _isUpdatingInvoiceItem = MutableStateFlow(-1)
    val isUpdatingInvoiceItem: StateFlow<Int> = _isUpdatingInvoiceItem

    init {
        getInvoices()
        getBusinesses()
        getCustomers()
        getTaxes()
    }

    private fun getInvoices() = viewModelScope.launch {
        _invoices.value = Resource.Loading
        _invoices.value = invoiceRepository.getInvoices()
    }

    private fun getBusinesses() = viewModelScope.launch {
        _businesses.value = Resource.Loading
        _businesses.value = businessRepository.getMyBusinessHolders()
    }

    private fun getCustomers() = viewModelScope.launch {
        _customers.value = Resource.Loading
        _customers.value = customersRepository.getCustomers()
    }

    private fun getTaxes() = viewModelScope.launch {
        _taxes.value = Resource.Loading
        _taxes.value = taxRepository.getTax()
    }

    fun validateInputs() {
        var valid = true
        if (desc.value.isEmpty()) {
            valid = false
        }
        if (qty.value.toDoubleOrNull() == null) {
            valid = false
        }
        if (price.value.toDoubleOrNull() == null) {
            valid = false
        }
        _areInputsValid.value = valid
    }

    fun setBusiness(businessModel: BusinessModel) {
        _invoice.value = _invoice.value.copy(businessModel = businessModel)
    }

    fun setCustomer(customer: CustomerModel) {
        _invoice.value = _invoice.value.copy(customer = customer)
        Log.e("t", "t")
    }

    fun setTax(tax: TaxModel) {
        _invoice.value = _invoice.value.copy(tax = tax)
    }

    fun addInvoiceItem() {
        val invoiceItem = InvoiceItemModel(desc.value, qty.value.toDouble(), price.value.toDouble())
        val items = _invoice.value.listOfItems + invoiceItem
        _invoice.value = _invoice.value.copy(listOfItems = items)
        desc.value = ""
        qty.value = ""
        price.value = ""
        validateInputs()
    }

    fun initUpdateInvoiceItem(index: Int) {
        val currentItem = _invoice.value.listOfItems[index]
        desc.value = currentItem.desc
        qty.value = currentItem.qty.toString()
        price.value = currentItem.price.toString()
        validateInputs()
        _isUpdatingInvoiceItem.value = index
    }

    fun updateInvoiceItem() {
        val invoiceItem = InvoiceItemModel(desc.value, qty.value.toDouble(), price.value.toDouble())
        val updatedItems = _invoice.value.listOfItems.toMutableList().also {
            it.removeAt(_isUpdatingInvoiceItem.value)
            it.add(_isUpdatingInvoiceItem.value, invoiceItem)
        }
        _invoice.value = _invoice.value.copy(listOfItems = updatedItems)
        desc.value = ""
        qty.value = ""
        price.value = ""
        validateInputs()
        _isUpdatingInvoiceItem.value = -1
    }

    fun deleteInvoiceItem(index: Int) {
        val updatedItemList = _invoice.value.listOfItems.toMutableList().also {
            it.removeAt(index)
        }
        _invoice.value = _invoice.value.copy(listOfItems = updatedItemList)
    }

    fun createOrUpdateInvoice() = viewModelScope.launch {
        _createInvoice.value = Resource.Loading
        if (_invoice.value.id.isEmpty())
            _createInvoice.value = invoiceRepository.addInvoice(_invoice.value)
        else
            _createInvoice.value = invoiceRepository.updateInvoice(_invoice.value)
        getInvoices()
    }

    fun canCreateInvoice(): Boolean {
        val invoice = _invoice.value
        return invoice.businessModel != null && invoice.customer != null && invoice.tax != null && invoice.listOfItems.isNotEmpty()
    }

    fun deleteInvoice(id: String) = viewModelScope.launch {
        invoiceRepository.deleteInvoice(id)
        getInvoices()
    }

    fun updatePaidState(id: String, isPaid: Boolean) = viewModelScope.launch {
        invoiceRepository.updatePaidState(id, isPaid)
        getInvoices()
    }

    fun initInvoiceUpdate(invoice: InvoiceModel) {
        _invoice.value = invoice
    }

    fun initNewInvoice() {
        _invoice.value = InvoiceModel()
    }

    fun setCurrentInvoice(invoice: InvoiceModel) {
        _invoice.value = invoice
    }
}