package com.cassiobruzasco.cryptocoinschallenge.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.cassiobruzasco.cryptocoinschallenge.data.model.Coin
import com.cassiobruzasco.cryptocoinschallenge.ui.screen.details.CoinDetails
import com.cassiobruzasco.cryptocoinschallenge.ui.screen.home.CoinHome
import com.cassiobruzasco.cryptocoinschallenge.ui.theme.CryptoCoinChallengeTheme

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CryptoCoinChallengeTheme {

                val uiState by viewModel.coinsUiState.collectAsStateWithLifecycle()
                var showDetails by rememberSaveable { mutableStateOf<Coin?>(null) }

                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    Box(
                        modifier = Modifier.fillMaxSize().padding(innerPadding)
                    ) {
                        showDetails?.let {
                            CoinDetails(it) {
                                showDetails = null
                            }
                        } ?: run {
                            CoinHome(
                                uiState = uiState,
                                onRefresh = { viewModel.getCoins(true) },
                                goToCoinDetails = {
                                    showDetails = it
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}