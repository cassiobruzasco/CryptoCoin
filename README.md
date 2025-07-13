# CryptoCoin

A simple Android app built with Kotlin and Jetpack Compose that displays a list of cryptocurrencies using data from the CoinGecko API.

## Features

- Fetches real-time coin data from the CoinGecko public API
- Displays coin name and symbol in a clean list
- Displays coin details on click
- Supports pull-to-refresh to reload data
- Graceful handling of loading, empty, and error states
- Clean MVVM architecture with `ViewModel`, `Repository`, and `Room` for caching

## Tech Stack

- Kotlin
- Jetpack Compose (UI)
- Retrofit + OkHttp (networking)
- Room (local storage)
- Coroutines + Flow (async and reactive data)
- Mockito + JUnit (unit testing)

## Getting Started

1. Clone the repo:
   ```bash
   git@github.com:cassiobruzasco/CryptoCoin.git