package emperorfin.android.weatherapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import emperorfin.android.weatherapp.ui.screen.home.HomeScreen
import emperorfin.android.weatherapp.ui.screen.home.HomeViewModel
import emperorfin.android.weatherapp.ui.screen.splash.SplashScreen
import emperorfin.android.weatherapp.ui.screen.weather.WeatherScreen
import emperorfin.android.weatherapp.ui.screen.weather.WeatherViewModel

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            NavHost(navController, startDestination = "splash") {
                composable("splash") { SplashScreen(navController) }
                composable("home") {
                    val homeViewModel: HomeViewModel = hiltViewModel()
                    HomeScreen(homeViewModel, navController)
                }
                composable("weather/{city}") { backStackEntry ->
                    val city = backStackEntry.arguments?.getString("city") ?: ""
                    val weatherViewModel: WeatherViewModel = hiltViewModel()
                    WeatherScreen(city, weatherViewModel)
                }
            }
        }
    }
}