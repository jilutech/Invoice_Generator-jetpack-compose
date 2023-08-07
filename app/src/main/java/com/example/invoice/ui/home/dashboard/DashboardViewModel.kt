package com.example.invoice.ui.home.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.invoice.data.Resource
import com.example.invoice.data.home.models.Dashboard
import com.example.invoice.data.home.repo.DashboardRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val repository: DashboardRepository
) : ViewModel() {

    private val _dashboard = MutableStateFlow<Resource<Dashboard>?>(null)
    val dashboard: StateFlow<Resource<Dashboard>?> = _dashboard

    init {
        getDashboardInfo()
    }

    private fun getDashboardInfo() = viewModelScope.launch {
        _dashboard.value = Resource.Loading
        _dashboard.value = repository.getDashboardInfo()
    }
}