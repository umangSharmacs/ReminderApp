package com.umang.reminderapp.data.models

import android.content.Context
import android.widget.Toast
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
    fun login(email: String, password: String, context: Context) {
        AuthManager.login(email, password, context)
        user = getFirebaseUser()
    }

    // Sign Up
    fun register(email: String, password: String,context: Context) {
        AuthManager.register(email, password, context)
        user = getFirebaseUser()
    }

    // Sign in Anonymously
    fun signInAnonymously(context: Context) {
        AuthManager.signInAnonymously(context)
        user = getFirebaseUser()
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