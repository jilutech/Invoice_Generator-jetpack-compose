package com.example.invoice.data.home.models

import com.example.invoice.data.home.repo.models.BaseModel

data class Business(
    val name : String = "",
    val address : String = "",
    val phone : String = "",
    val email : String = ""
) : BaseModel(){


    fun getCompleteAddress() : String{
        return "$address\n phone : $phone \n name : $name \n email : $email"
    }
}
