package com.example.invoice.test

import androidx.test.core.app.ApplicationProvider
import android.content.Context
import com.google.common.truth.Truth.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test

class ResourceCompareTest{
    private lateinit var resourceCompare: ResourceCompare


    @Before
    fun setup(){
        resourceCompare = ResourceCompare()
    }

    @After
    fun tearDown(){

    }

    @Test
    fun stringResourceSameAsGiveString_returnTrue(){

        val context = ApplicationProvider.getApplicationContext<Context>()
        val result = resourceCompare.isEqual(context = context, com.example.invoice.R.string.app_name,"Invoice Track")
        assertThat(result).isTrue()
    }

    @Test
    fun stringResourceNotSameAsGiveString_returnFalse(){

        val context = ApplicationProvider.getApplicationContext<Context>()
        val result = resourceCompare.isEqual(context = context, com.example.invoice.R.string.app_name,"Invoice Tracks")
        assertThat(result).isFalse()
    }
}