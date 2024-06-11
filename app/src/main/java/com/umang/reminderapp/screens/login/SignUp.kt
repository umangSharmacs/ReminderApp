package com.umang.reminderapp.screens.login

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.umang.reminderapp.R
import com.umang.reminderapp.data.models.AuthViewModel

@Composable
fun SignUpScreen(modifier: Modifier = Modifier,
                 navController: NavHostController,
                 AuthViewModel: AuthViewModel
) {

    var emailText by remember { mutableStateOf("") }
    var passwordText by remember { mutableStateOf("") }

    val context = LocalContext.current

    Surface(modifier = modifier) {
        Column(modifier = Modifier
                .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally) {

            // Email
            OutlinedTextField(
                    value = emailText,
                    onValueChange = {emailText = it},
                    label = { Text(text = "Email") },
                    modifier = Modifier
                            .padding(15.dp)
            )

            // Password
            OutlinedTextField(
                    value = passwordText,
                    onValueChange = {passwordText = it},
                    label = { Text(text = "Password") },
                    modifier = Modifier
                            .padding(15.dp)
            )

            // Sign up Button
            Button(onClick = {
                if(emailText!="" && passwordText!=""){
                    if(AuthViewModel.user==null){
                        AuthViewModel.register(emailText, passwordText, context)
                        if (AuthViewModel.isLoggedIn()){
                            navController.navigate("Home")
                        }
                    }else if(AuthViewModel.user!!.isAnonymous){
                        AuthViewModel.convertAnonymousUserToPermanentUserWithEmail(
                            emailText,
                            passwordText,
                            context
                        )
                        if (AuthViewModel.isLoggedIn()){
                            navController.navigate("Home")
                        }
                    }
                }
            },
                modifier = Modifier
                    .padding(15.dp)
            ) {
                Text(text = "Sign Up")
            }

            // Already have an account
            Row(modifier = Modifier
                .align(Alignment.CenterHorizontally),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ){
                Text(text = "Already have an account?")
                TextButton(onClick = { navController.navigate("LoginScreen") }) {
                    Text(text = "Login")
                }
            }

            // Or
            Row(modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(bottom=10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Divider(modifier = Modifier
                    .width(50.dp)
                    .padding(end = 5.dp),
                    thickness = 1.dp )
                Text(text = "OR")
                Divider(modifier = Modifier
                    .width(50.dp)
                    .padding(start = 5.dp),
                    thickness = 1.dp )

            }

            // Text
            Text(text = "Sign in using other methods")

            // Other Sign in Options
            Row(modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ){

                // Anonymous Sign in 
                IconButton(modifier = Modifier
                    .padding(5.dp),
                    onClick = {
                        AuthViewModel.signInAnonymously(context)
                        if(AuthViewModel.isLoggedIn()){
                            navController.navigate("home")
                        }
                    }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.user),
                        contentDescription = "Anonymous User",
                        tint = Color.Unspecified
                    )
                }
                //Divider
                Divider(modifier = Modifier
                    .height(50.dp)
                    .width(1.dp),
                    thickness = 2.dp )
                // Google Sign in 
                IconButton(modifier = Modifier
                    .padding(5.dp),
                    onClick = { /*TODO*/ }) {
                    Icon(
                        painter = painterResource(id = R.drawable.google_logo),
                        contentDescription = "Google",
                        tint = Color.Unspecified
                    )
                }

            }


    }

}}
