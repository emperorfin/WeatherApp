package emperorfin.android.weatherapp.ui.screen.weather

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import emperorfin.android.weatherapp.data.repository.WeatherRepository
import emperorfin.android.weatherapp.domain.model.WeatherModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject


@HiltViewModel
class WeatherViewModel @Inject constructor(private val repo: WeatherRepository) : ViewModel() {
//    private val _weather = MutableStateFlow<WeatherModel?>(null)
//    val weather: StateFlow<WeatherModel?> = _weather
//
//    fun fetchWeather(city: String) {
//
//        viewModelScope.launch {
//            _weather.value = repo.getWeather(city)
//        }
//    }

    private val _weather = MutableStateFlow<WeatherModel?>(null)
    val weather: StateFlow<WeatherModel?> = _weather

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    fun fetchWeather(city: String) {
        viewModelScope.launch {
            _loading.value = true
            try {
                val response = repo.getWeather(city)
                _weather.value = response
                _errorMessage.value = null
            } catch (e: Exception) {
                _errorMessage.value = when (e) {
                    is HttpException -> "Invalid API key or city name."
                    is IOException -> "No internet connection."
                    else -> "Something went wrong."
                }
            } finally {
                _loading.value = false
            }
        }
    }


}
