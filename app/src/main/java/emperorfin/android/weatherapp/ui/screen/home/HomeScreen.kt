package emperorfin.android.weatherapp.ui.screen.home

import android.app.Application
import android.widget.Toast
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import emperorfin.android.weatherapp.ui.util.InternetConnectivityUtil.hasInternetConnection


@Composable
fun HomeScreen(viewModel: HomeViewModel, navController: NavController) {
    val context = LocalContext.current

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

            if(city.isEmpty()) {
                Toast.makeText(context, "Please enter city.", Toast.LENGTH_SHORT).show()

                return@Button
            }

            if (!hasInternetConnection(context.applicationContext as Application)) {
                Toast.makeText(context, "No internet connectivity.", Toast.LENGTH_SHORT).show()

                return@Button
            }

            viewModel.saveCity()

            navController.navigate("weather/${city}")
        }) {
            Text("Get Weather")
        }
    }
}