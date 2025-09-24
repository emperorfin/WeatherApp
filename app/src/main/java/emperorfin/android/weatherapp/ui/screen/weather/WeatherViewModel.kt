package emperorfin.android.weatherapp.ui.screen.weather

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import emperorfin.android.weatherapp.data.repository.WeatherRepository
import emperorfin.android.weatherapp.di.IoDispatcher
import emperorfin.android.weatherapp.domain.model.WeatherModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val repo: WeatherRepository,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _weather = MutableStateFlow<WeatherModel?>(null)
    val weather: StateFlow<WeatherModel?> = _weather

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    fun fetchWeather(city: String) {
        viewModelScope.launch(dispatcher) {
            _loading.value = true
            try {
                val response = repo.getWeather(city)
                _weather.value = response
                _errorMessage.value = null
            } catch (e: Exception) {
                _errorMessage.value = "Something went wrong."
            } finally {
                _loading.value = false
            }
        }
    }


}
