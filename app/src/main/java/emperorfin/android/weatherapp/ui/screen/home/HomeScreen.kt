package emperorfin.android.weatherapp.ui.screen.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController


@Composable
fun HomeScreen(viewModel: HomeViewModel, navController: NavController) {
    val city by viewModel.city.collectAsState()

    Column(modifier = Modifier.padding(16.dp).fillMaxSize()) {
        Spacer(modifier = Modifier.height(28.dp))

        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = city,
            onValueChange = { viewModel.updateCity(it) },
            label = { Text("Enter City") }
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = {
            viewModel.saveCity()
            navController.navigate("weather/${city}")
        }) {
            Text("Get Weather")
        }
    }
}