package com.example.invoice.data.home.models

import com.example.invoice.data.home.repo.models.BaseModel

data class TaxModel(
    val desc : String = "",
    val value : Double = 0.0
) : BaseModel(){
       val taxLabel
           get() = "$desc($value %)"

       val taxValue
           get() = "$value %"
}
