package com.umang.reminderapp.singletons

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.ktx.Firebase

object AuthManager {


    var auth = FirebaseAuth.getInstance()

    var user = auth.currentUser

    fun isLoggedIn(): Boolean {
        return user != null
    }

    fun register(email: String, password: String){
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if(task.isSuccessful){
                        user=auth.currentUser

                    }
                }
    }

    fun login(email: String, password: String){
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if(task.isSuccessful){
                        user = auth.currentUser
                    }
                }
    }

    fun logout(){
        auth.signOut()
        user = null
    }

    fun getFirebaseUser(): FirebaseUser? {
        if (user == null) {
            return null
        }
        return user
    }
}
