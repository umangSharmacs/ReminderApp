package com.umang.reminderapp.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import com.umang.reminderapp.data.models.AuthViewModel


@Composable
fun ProfilePage(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues,
    navHost: NavHostController,
    authViewModel: AuthViewModel
) {

    val user = authViewModel.user

    Column(modifier = modifier
        .fillMaxSize()
        .padding(top = paddingValues.calculateTopPadding()),
        horizontalAlignment = Alignment.CenterHorizontally
    ){

        if (user!=null){
            Text(text = "User email - "+user.email.toString(), color = Color.Black)
        } else {
            Text(text = "Not Signed In", color = Color.Black)
        }

        Button(onClick = {
            authViewModel.logout()
            navHost.navigate(route = "SignUpScreen")
        }) {
            Text(text = "Sign Out")
        }

        if (user != null && user.isAnonymous) {

            Button(onClick = {
                navHost.navigate(route = "SignUpScreen")
            }) {
                Text(text = "Sign up ")
            }
        }
    }
}
