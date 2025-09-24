package emperorfin.android.weatherapp.ui.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import emperorfin.android.weatherapp.data.local.Preferences
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(private val pref: Preferences) : ViewModel() {
    private val _city = MutableStateFlow("")
    val city: StateFlow<String> = _city

    init {
        viewModelScope.launch {
            pref.cityFlow.collect { _city.value = it }
        }
    }

    fun updateCity(newCity: String) {
        _city.value = newCity
    }

    fun saveCity() {
        viewModelScope.launch {
            pref.saveCity(_city.value)
        }
    }
}
