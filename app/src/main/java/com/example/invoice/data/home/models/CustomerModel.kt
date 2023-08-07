package com.example.invoice.data.home.repo.models

data class CustomerModel(
    var name: String = "",
    var address: String = "",
    var phone: String = "",
    var mailId: String = ""
) : BaseModel(){

    fun getCompleteAddress() : String{
        return "$name\n$address\n$phone"
    }
}