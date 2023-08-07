package com.example.invoice.data.home.repo

import com.example.invoice.data.Resource
import com.example.invoice.data.home.models.Dashboard

interface DashboardRepository {
    suspend fun getDashboardInfo(): Resource<Dashboard>
}