package com.umang.reminderapp.data.models

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser
import com.umang.reminderapp.data.classes.ScreenState
import com.umang.reminderapp.singletons.AuthManager
import com.umang.reminderapp.util.Resource
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class AuthViewModel(
): ViewModel() {

    var user: FirebaseUser? = null

    private val _signUpState = Channel<ScreenState>()
    val signUpState = _signUpState.receiveAsFlow()

    init{
        user = getFirebaseUser()
    }

    // Get User
    fun getFirebaseUser(): FirebaseUser? {
        return AuthManager.getFirebaseUser()
    }

    // Check if user is currently logged in
    fun isLoggedIn(): Boolean {
        return AuthManager.isLoggedIn()
    }

    // Log in
    fun login(email: String, password: String, context: Context) = viewModelScope.launch{
        AuthManager.login(email, password, context).collect{ result ->
            when(result){
                is Resource.Success -> {
                    _signUpState.send(ScreenState(isSuccess="Logged In"))
                    user = getFirebaseUser()
                }
                is Resource.Loading ->{
                    _signUpState.send(ScreenState(isLoading = true))
                }
                is Resource.Error -> {
                    _signUpState.send(ScreenState(isError = result.message))
                }
            }
        }
        user = getFirebaseUser()
    }

    // Sign Up
    fun register(email: String, password: String,context: Context) = viewModelScope.launch{
        AuthManager.register(email, password, context).collect{result ->
            when(result){
                is Resource.Success -> {
                    _signUpState.send(ScreenState(isSuccess="Signed Up Successfully"))
                    user = getFirebaseUser()
                }
                is Resource.Loading ->{
                    _signUpState.send(ScreenState(isLoading = true))
                }
                is Resource.Error -> {
                    _signUpState.send(ScreenState(isError = result.message))
                }
            }
        }
        user = getFirebaseUser()
    }

    // Sign in Anonymously
    fun signInAnonymously(context: Context) = viewModelScope.launch{
        AuthManager.signInAnonymously(context).collect{ result ->
            when(result){
                is Resource.Success -> {
                    _signUpState.send(ScreenState(isSuccess="Logged In Anonymously!"))
                    user = getFirebaseUser()
                }
                is Resource.Loading ->{
                    _signUpState.send(ScreenState(isLoading = true))
                }
                is Resource.Error -> {
                    _signUpState.send(ScreenState(isError = result.message))
                }
            }
        }
    }

    fun convertAnonymousUserToPermanentUserWithEmail(
        email: String,
        password: String,
        context: Context
    ){
        AuthManager.convertAnonymousUserToPermanentUserWithEmail(email, password, context)
        user = getFirebaseUser()
    }

    fun logout() {
        AuthManager.logout()
        user = null
        //Todo Toast
    }

}