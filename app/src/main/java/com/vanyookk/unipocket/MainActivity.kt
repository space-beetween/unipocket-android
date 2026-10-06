package com.vanyookk.unipocket

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.vanyookk.unipocket.data.remote.RetrofitClient
import com.vanyookk.unipocket.data.repository.AuthRepository
import com.vanyookk.unipocket.ui.navigation.AppNavGraph
import com.vanyookk.unipocket.ui.screens.SplashViewModel
import com.vanyookk.unipocket.ui.screens.SplashViewModelFactory
import com.vanyookk.unipocket.ui.theme.UninetTheme

class MainActivity : ComponentActivity() {
    private val viewModel: SplashViewModel by viewModels {
        SplashViewModelFactory(
            AuthRepository(RetrofitClient.authApi, applicationContext)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        RetrofitClient.init(this)

        splashScreen.setKeepOnScreenCondition {
            !viewModel.isReady.value
        }

        enableEdgeToEdge()
        setContent {
            UninetTheme {
                val startDestination by viewModel.startDestination.collectAsState()
                AppNavGraph(startDestination = startDestination)
            }
        }
    }
}
