package com.example.invoice.ui.auth

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import com.example.invoice.data.Resource
import com.example.invoice.data.auth.AuthRepository
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {

    private val _loginFlow = MutableStateFlow<Resource<FirebaseUser>?>(null)
    val loginFlow: StateFlow<Resource<FirebaseUser>?> = _loginFlow

    private val _signupFlow = MutableStateFlow<Resource<FirebaseUser>?>(null)
    val signupFlow: StateFlow<Resource<FirebaseUser>?> = _signupFlow

    private val _are_validate = MutableLiveData(false)
    val are_validate : LiveData<Boolean?> = _are_validate
    val currentUser: FirebaseUser?


        get() = repository.currentUser
    private val _loginState = MutableStateFlow(LoginState.IDLE)
    val loginState: StateFlow<LoginState>
        get() = _loginState
    init {
        if (repository.currentUser != null) {
            _loginFlow.value = Resource.Success(repository.currentUser!!)
        }
    }

    fun login(email: String, password: String){
        viewModelScope.launch {
            _loginFlow.value = Resource.Loading
            val result = repository.login(email, password)
            _loginFlow.value = result
        }
    }

    fun signup(name: String, email: String, password: String) = viewModelScope.launch {
        _signupFlow.value = Resource.Loading
        val result = repository.signup(name, email, password)
        _signupFlow.value = result
    }

    fun signUpValidation(name: String, email: String, password: String,onAuthenticated:()->Unit,onAuthenticatedFailed: (error : String) -> Unit){

        if ( name.trim().isNotEmpty() && password.trim().isNotEmpty() && email.trim().isNotEmpty()){
            _signupFlow.value = Resource.Loading
            viewModelScope.launch {
                repository.signup(
                    name, email, password
                ).let {
                    when(it){
                       is Resource.Success -> {
                           onAuthenticated()
                           _signupFlow.value = it
                        }
                      is Resource.Failure ->{
                             onAuthenticatedFailed(it.exception.message!!)
                             _signupFlow.value = null
                      }

                      else -> {}
                    }
                }
            }

        } else{
            onAuthenticatedFailed("it.exception.message!!")
            _signupFlow.value = null
        }
    }

    fun logout() {
        repository.logout()
        _loginFlow.value = null
        _signupFlow.value = null
    }
}
enum class LoginState {
    IDLE, SUCCESS, ERROR
}