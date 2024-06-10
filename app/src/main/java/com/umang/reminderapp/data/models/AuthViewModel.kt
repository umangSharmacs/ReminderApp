package com.umang.reminderapp.data.models

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser
import com.umang.reminderapp.singletons.AuthManager

class AuthViewModel: ViewModel() {

    var user: FirebaseUser? = null

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
    fun login(email: String, password: String) {
        AuthManager.login(email, password)
        user = getFirebaseUser()
    }

    // Sign Up
    fun register(email: String, password: String) {
        AuthManager.register(email, password)
        user = getFirebaseUser()
    }

    fun logout() {
        AuthManager.logout()
        user = null
        //Todo Toast
    }

}