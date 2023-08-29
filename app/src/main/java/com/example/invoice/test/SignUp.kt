package com.example.invoice.test

object SignUp {

    private val existingUser = listOf("jilu","rose")


    fun validateUserResgisteration(
         userName :String,
         password : String
    ) : Boolean{
        if (userName.isEmpty() || password.isEmpty()){
            return false
        }
        if(userName in existingUser) {
            return false
        }

        return true
    }
}