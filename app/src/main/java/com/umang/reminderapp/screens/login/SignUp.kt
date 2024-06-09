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
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController

@Composable
fun SignUpScreen(modifier: Modifier = Modifier,
                 navController: NavHostController) {

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

            Button(onClick = { },
                    modifier = Modifier
                            .padding(15.dp)) {
                Text(text = "Sign Up")
            }
        }
    }

}