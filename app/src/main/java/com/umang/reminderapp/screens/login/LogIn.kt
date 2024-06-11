package com.umang.reminderapp.screens.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.umang.reminderapp.data.models.AuthViewModel

@Composable
fun LogInScreen(modifier: Modifier = Modifier,
                navController: NavHostController,
                AuthViewModel: AuthViewModel
) {

    var emailText by remember { mutableStateOf("") }
    var passwordText by remember { mutableStateOf("") }

    Surface(modifier = modifier) {

        Column(modifier = Modifier
                .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally) {

            OutlinedTextField(
                    value = emailText,
                    onValueChange = {emailText = it},
                    label = { Text(text = "Email") },
                    modifier = Modifier
                            .padding(15.dp)
            )

            OutlinedTextField(
                    value = passwordText,
                    onValueChange = {passwordText = it},
                    label = { Text(text = "Password") },
                    modifier = Modifier
                            .padding(15.dp)
            )

            Button(onClick = {
                if(emailText!="" && passwordText!=""){
                    AuthViewModel.login(emailText, passwordText)
                    if (AuthViewModel.isLoggedIn()){
                        navController.navigate("Home")
                    }
                }else{
                    //TODO
//                    Toast.makeText( this@MainActivity,"Please fill all the fields", Toast.LENGTH_SHORT).show()
                }},
                    modifier = Modifier
                            .padding(15.dp)) {
                Text(text = "Log In")
            }
        }
    }

}