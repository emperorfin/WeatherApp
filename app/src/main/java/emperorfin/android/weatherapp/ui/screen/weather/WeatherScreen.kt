package emperorfin.android.weatherapp.ui.screen.weather

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


@Composable
fun WeatherScreen(city: String, viewModel: WeatherViewModel) {

    val weather by viewModel.weather.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()
    val isLoading by viewModel.loading.collectAsState()

    LaunchedEffect(city) {
        viewModel.fetchWeather(city)
    }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        when {
            isLoading -> {
                CircularProgressIndicator()
            }
            errorMessage != null -> {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = errorMessage ?: "", color = Color.Red)
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(onClick = { viewModel.fetchWeather(city) }) {
                        Text("Retry")
                    }
                }
            }
            weather != null -> {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("City: ${weather!!.name}")
                    Text("Temperature: ${weather!!.main.temp}°C")
                    Text("Description: ${weather!!.weather.first().description}")
                }
            }
        }
    }
}
