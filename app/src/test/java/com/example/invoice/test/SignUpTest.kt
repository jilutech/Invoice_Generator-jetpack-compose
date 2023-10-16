package com.example.invoice.test

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class SignUpTest{

    @Test
    fun `empty userName returns false`(){

        val result = SignUp.validateUserResgisteration(
            "",
            ""
        )
        assertThat(result).isFalse()
    }

    @Test
    fun `correct userName and password returns true`(){

        val result = SignUp.validateUserResgisteration(
            "jilus",
            "123"
        )
        assertThat(result).isTrue()
    }

    @Test
    fun `userName existing return false`(){

        val result = SignUp.validateUserResgisteration(
            "rose",
            "123"
        )
        assertThat(result).isFalse()
    }

}