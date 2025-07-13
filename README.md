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

- **Kotlin:** 2.2.0
- **Jetpack Compose:** BOM 2025.06.01 (Material 3)
- **Retrofit:** 3.0.0
- **Room:** 2.7.2
- **Coil (image loading):** 2.7.0
- **Unit Testing:** JUnit 4.13.2, Mockito 5.4.0, Coroutines Test 1.10.2
- **Gradle Plugin (AGP):** 8.1.1
- **Min SDK:** 28
- **Target SDK:** 36
- **Compile SDK:** 36

> ✅ CI-ready — clean build runs with:
   ```bash
   ./gradlew build
   ```
   
## Getting Started

1. Clone the repo:
   ```bash
   git clone git@github.com:cassiobruzasco/CryptoCoin.git

2. Add your DEMO CoinGecko API key:
   - In local.properties: `COIN_GECKO_API_KEY=your_api_key_here`

3. Build and run the app in Android Studio.
   - Click "Run" in Android Studio
   - Or run from terminal:
 ```bash
  ./gradlew installDebug
 ```
## 📱 Screenshots

<table>
  <tr>
    <td align="center"><strong>Home</strong></td>
    <td align="center"><strong>Details</strong></td>
  </tr>
  <tr>
    <td>
      <img src="https://github.com/user-attachments/assets/4b7dfcf8-524e-43e6-affb-8febff98cab9" width="360" height="780" alt="Home Screen"/>
    </td>
    <td>
      <img src="https://github.com/user-attachments/assets/2c59e7c0-4066-4f9f-ac06-44dcb855bea2" width="360" height="780" alt="Details Screen"/>
    </td>
  </tr>
</table>


