package com.umang.reminderapp.screens.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun SignUpLanding(modifier: Modifier = Modifier,
                  navController: NavHostController) {

    Surface(modifier = modifier) {

        Column(modifier = Modifier
                .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally) {

            Button(onClick = { navController.navigate("LogInScreen") },
                    modifier = Modifier
                            .padding(15.dp)) {
                Text(text = "Login")
            }

            Button(onClick = { navController.navigate("SignUpScreen")},
                    modifier = Modifier
                            .padding(15.dp)) {
                Text(text = "Signup")
            }
            Button(onClick = { /*TODO*/ },
                    modifier = Modifier
                            .padding(15.dp)) {
                Text(text = "Sign in Anonymously")
            }

        }
    }

}