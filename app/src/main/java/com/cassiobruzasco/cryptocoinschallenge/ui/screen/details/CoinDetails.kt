package com.cassiobruzasco.cryptocoinschallenge.ui.screen.details

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage
import com.cassiobruzasco.cryptocoinschallenge.R
import com.cassiobruzasco.cryptocoinschallenge.data.model.Coin

@Composable
fun CoinDetails(
    coin: Coin,
    onBackClick: () -> Unit
) {
    BackHandler { onBackClick() }
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.weight(1f))
        SubcomposeAsyncImage(
            modifier = Modifier.fillMaxWidth().size(122.dp).padding(10.dp),
            model = coin.image,
            contentDescription = "Coin Image",
            contentScale = ContentScale.Fit,
            loading = {
                CircularProgressIndicator(
                    modifier = Modifier.scale(0.2f).padding(20.dp),
                )
            },
            error = {
                Text(stringResource(R.string.no_coin_image))
            }
        )
        Text(
            modifier = Modifier.padding(10.dp),
            text = "${coin.name} (${coin.symbol})",
            style = TextStyle(
                fontSize = 22.sp,
                fontWeight = FontWeight(700),
            )
        )
        coin.currentPrice?.let { price ->
            Text(
                modifier = Modifier.padding(10.dp),
                text = stringResource(R.string.details_coin_price, price),
                style = TextStyle(
                    fontSize = 22.sp,
                    fontWeight = FontWeight(700),
                ),
                textAlign = TextAlign.Center
            )
        }
        Text(
            modifier = Modifier.padding(horizontal = 30.dp, vertical = 10.dp),
            text = stringResource(R.string.details_coin_description),
            style = TextStyle(
                fontSize = 12.sp,
            ),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.weight(1f))
        Button(
            modifier = Modifier.fillMaxWidth().padding(20.dp),
            onClick = onBackClick
        ) {
            Text(text = stringResource(R.string.back_to_all_coins))
        }
    }
}