package com.umang.reminderapp.singletons

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.ui.platform.LocalContext
import com.google.firebase.auth.EmailAuthCredential
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.ktx.Firebase

object AuthManager {


    var auth = FirebaseAuth.getInstance()

    var user = auth.currentUser

    fun isLoggedIn(): Boolean {
        return user != null
    }

    fun register(email: String, password: String,context: Context){
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if(task.isSuccessful){
                        user=auth.currentUser
                        Toast.makeText(context, "Registration successful", Toast.LENGTH_SHORT).show()
                    }
                    if(task.isCanceled){
                        Toast.makeText(context, "Cancelled"+task.exception, Toast.LENGTH_SHORT).show()
                    }
                }
            .addOnFailureListener {
                Toast.makeText(context, "Failed: ${it.message}", Toast.LENGTH_LONG).show()
            }
    }

    fun login(email: String, password: String, context: Context){
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if(task.isSuccessful){
                        user = auth.currentUser
                        Toast.makeText(context, "Login successful", Toast.LENGTH_SHORT).show()
                    }
                    if(task.isCanceled){
                        Toast.makeText(context, "Cancelled"+task.exception, Toast.LENGTH_SHORT).show()
                    }
                }
    }

    fun signInAnonymously(context: Context) {
        auth.signInAnonymously()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    user = auth.currentUser
                    Toast.makeText(context, "Logged in Anonymously", Toast.LENGTH_SHORT).show()
                }
                if (task.isCanceled) {
                    Toast.makeText(context, "Cancelled" + task.exception, Toast.LENGTH_SHORT).show()
                }
            }
    }

    fun convertAnonymousUserToPermanentUserWithEmail(
        email: String,
        password: String,
        context: Context) {
        val credential = EmailAuthProvider.getCredential(email, password)

        auth.currentUser!!.linkWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("TAG", "linkWithCredential:success")
                    user = auth.currentUser
                } else {
                    Log.w("TAG", "linkWithCredential:failure", task.exception)
                    Toast.makeText(
                        context,
                        "Authentication failed.",
                        Toast.LENGTH_SHORT,
                    ).show()
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
