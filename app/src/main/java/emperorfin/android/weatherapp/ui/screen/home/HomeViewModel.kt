package emperorfin.android.weatherapp.ui.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import emperorfin.android.weatherapp.data.local.Preferences
import emperorfin.android.weatherapp.di.IoDispatcher
import emperorfin.android.weatherapp.di.MainDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val pref: Preferences,
    @MainDispatcher private val dispatcherMain: CoroutineDispatcher,
    @IoDispatcher private val dispatcherIo: CoroutineDispatcher,
) : ViewModel() {

    private val _city = MutableStateFlow("")
    val city: StateFlow<String> = _city

    init {
        viewModelScope.launch(dispatcherMain) {
            pref.cityFlow.collect {
                _city.value = it
            }
        }
    }

    fun updateCity(newCity: String) {
        _city.value = newCity
    }

    fun saveCity() {
        viewModelScope.launch(dispatcherIo) {
            pref.saveCity(_city.value)
        }
    }
}
