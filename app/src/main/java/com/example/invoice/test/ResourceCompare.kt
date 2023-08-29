package com.example.invoice.test

import android.content.Context

class ResourceCompare {

    fun isEqual(context : Context,resId : Int,stringName : String) : Boolean{
        return context.getString(resId) == stringName
    }
}