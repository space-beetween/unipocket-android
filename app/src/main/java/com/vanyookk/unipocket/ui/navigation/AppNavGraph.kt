package com.vanyookk.unipocket.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.vanyookk.unipocket.data.repository.AuthRepository
import com.vanyookk.unipocket.data.remote.RetrofitClient.authApi
import com.vanyookk.unipocket.ui.screens.MainScreen
import com.vanyookk.unipocket.ui.screens.LoginScreen
import com.vanyookk.unipocket.ui.screens.LoginViewModel

@Composable
fun AppNavGraph(startDestination: String) {
    val navController = rememberNavController()
    val context = LocalContext.current

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable("login") {
            val authRepository = AuthRepository(authApi, context)
            val viewModel = LoginViewModel(authRepository)

            LoginScreen(
                viewModel = viewModel,
                onLoginSuccess = {
                    navController.navigate("main") {
                        popUpTo("login") { inclusive = true }
                    }
                }
            )
        }

        composable("main") {
            MainScreen()
        }
    }
}