package com.umang.reminderapp.screens.login

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.loader.content.Loader
import androidx.navigation.NavHostController
import com.umang.reminderapp.R
import com.umang.reminderapp.data.models.AuthViewModel
import com.umang.reminderapp.ui.theme.robotoMonoFontFamily
import kotlinx.coroutines.launch

@Composable
fun LogInScreen(modifier: Modifier = Modifier,
                navController: NavHostController,
                AuthViewModel: AuthViewModel
) {

    var emailText by remember { mutableStateOf("") }
    var passwordText by remember { mutableStateOf("") }
    val context = LocalContext.current
    val state = AuthViewModel.signUpState.collectAsState(initial = null)
    val scope = rememberCoroutineScope()

    Surface(modifier = modifier) {

        Column(modifier = Modifier
            .fillMaxSize()
            .padding(start = 60.dp, top = 75.dp),
            horizontalAlignment = Alignment.Start,
        ){
            // Memento
            Text(modifier = Modifier,
                textAlign = TextAlign.Start,
                fontFamily = robotoMonoFontFamily,
                fontWeight = FontWeight.Black,
                fontSize = 40.sp,
                text = "Memento",
                color = MaterialTheme.colorScheme.primary
            )

            // Conquer Chaos
            Text(modifier = Modifier,
                textAlign = TextAlign.Start,
                fontFamily = robotoMonoFontFamily,
                fontWeight = FontWeight.Black,
                fontSize = 20.sp,
                text = "Conquer Chaos",
                color = MaterialTheme.colorScheme.primary
            )

            // Log in
            Text(modifier = Modifier.padding(top=20.dp),
                textAlign = TextAlign.Start,
                fontFamily = robotoMonoFontFamily,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 40.sp,
                text = "Log in",
                color = MaterialTheme.colorScheme.primary
            )
        }

        Column(modifier = Modifier
                .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally) {

            OutlinedTextField(
                value = emailText,
                onValueChange = {emailText = it},
                label = { Text(text = "Email") },
                modifier = Modifier
                        .padding(15.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.primary,
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    disabledContainerColor = Color.White
                )
            )

            OutlinedTextField(
                value = passwordText,
                onValueChange = {passwordText = it},
                label = { Text(text = "Password") },
                modifier = Modifier
                        .padding(15.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.primary,
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    disabledContainerColor = Color.White
                )
            )

            Button(onClick = {
                if(emailText!="" && passwordText!=""){
                    scope.launch{
                        AuthViewModel.login(emailText, passwordText, context)
                    }

                    if (AuthViewModel.isLoggedIn()){
                        navController.navigate("Home")
                    }
                } },
                modifier = Modifier
                    .padding(15.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = Color.White
                )

                ) {
                Text(text = "Log In")
            }

            // Don't have an Account?
            Row(modifier = Modifier
                .align(Alignment.CenterHorizontally),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ){
                Text(text = "Don't have an Account?")
                TextButton(onClick = { navController.navigate("SignUpScreen") }) {
                    Text(text = "Create one!")
                }
            }

            // Or
            Row(modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(bottom = 10.dp),
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
    }

    // Effect for Log in
    LaunchedEffect(key1 = state.value?.isSuccess) {
        scope.launch {
            if (state.value?.isSuccess?.isNotEmpty() == true) {
                val success = state.value?.isSuccess
                // TODO use Snackbar
                Toast.makeText(context, state.value?.isSuccess.toString(), Toast.LENGTH_LONG).show()
                navController.navigate("Home")
            }
        }
    }

}