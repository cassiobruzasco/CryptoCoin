package com.cassiobruzasco.cryptocoinschallenge.ui.screen.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.pullToRefresh
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cassiobruzasco.cryptocoinschallenge.R
import com.cassiobruzasco.cryptocoinschallenge.data.model.Coin
import com.cassiobruzasco.cryptocoinschallenge.ui.UiState
import com.cassiobruzasco.cryptocoinschallenge.ui.theme.CryptoCoinChallengeTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CoinHome(
    uiState: UiState,
    onRefresh: () -> Unit,
    goToCoinDetails: (Coin) -> Unit
) {
    val pullRefreshState = rememberPullToRefreshState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .pullToRefresh(
                isRefreshing = uiState is UiState.Loading,
                state = pullRefreshState,
                onRefresh = onRefresh
            ),
        contentAlignment = Alignment.Center
    ) {
        when (val data = uiState) {

            is UiState.Error -> {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(text = stringResource(R.string.home_coin_error, data.message))

                    Spacer(Modifier.height(20.dp))

                    Button(
                        onClick = onRefresh
                    ) {
                        Text(text = stringResource(R.string.retry))
                    }
                }
            }

            is UiState.Loading -> {
                if (!data.isRefresh) CircularProgressIndicator()
            }

            is UiState.Success -> {
                if (data.coins.isEmpty()) {
                    Text(
                        modifier = Modifier
                            .padding(10.dp)
                            .fillMaxWidth(),
                        text = stringResource(R.string.home_coin_empty),
                        style = TextStyle(
                            fontSize = 22.sp,
                            fontWeight = FontWeight(700),
                        ),
                        textAlign = TextAlign.Center
                    )
                } else {
                    LazyColumn {
                        item {
                            Text(
                                modifier = Modifier
                                    .padding(10.dp)
                                    .fillMaxWidth(),
                                text = stringResource(R.string.home_coin_title),
                                style = TextStyle(
                                    fontSize = 22.sp,
                                    fontWeight = FontWeight(700),
                                ),
                                textAlign = TextAlign.Center
                            )
                        }

                        items(
                            items = data.coins,
                            key = { coin -> coin.id }
                        ) {
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 10.dp, horizontal = 30.dp)
                                    .clickable {
                                        goToCoinDetails(it)
                                    },
                            ) {
                                val coinText = it.name ?: stringResource(R.string.no_coin_name)
                                val symbolText = it.symbol ?: stringResource(R.string.no_coin_symbol)
                                Text(
                                    modifier = Modifier
                                        .padding(10.dp)
                                        .fillMaxWidth(),
                                    text = "$coinText ($symbolText)",
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    }
                }
            }
        }

        PullToRefreshBox(
            isRefreshing = (uiState as? UiState.Loading)?.isRefresh ?: false,
            state = pullRefreshState,
            modifier = Modifier.align(Alignment.TopCenter),
            onRefresh = onRefresh
        ) {

        }
    }

}

@Preview
@Composable
private fun CoinHomePreview() {
    CryptoCoinChallengeTheme {
        val coins = listOf(
            Coin(
                id = "01",
                name = "Bitcoin",
                symbol = "BTC"
            ),
            Coin(
                id = "02",
                name = "Ethereum",
                symbol = "ETH"
            )
        )
        CoinHome(
            uiState = UiState.Success(coins),
            onRefresh = {},
            goToCoinDetails = {}
        )
    }
}