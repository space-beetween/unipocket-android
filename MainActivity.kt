package com.vanyookk.uninet

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.vanyookk.uninet.data.local.TokenManager
import com.vanyookk.uninet.data.remote.RetrofitClient
import com.vanyookk.uninet.ui.navigation.AppNavigation
import com.vanyookk.uninet.ui.theme.UninetTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val tokenManager = TokenManager.getInstance(applicationContext)
        RetrofitClient.init(tokenManager)

        setContent {
            UninetTheme {
                AppNavigation()
            }
        }
    }
}
