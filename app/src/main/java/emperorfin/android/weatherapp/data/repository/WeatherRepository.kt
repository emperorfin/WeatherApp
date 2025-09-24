package emperorfin.android.weatherapp.data.repository

import emperorfin.android.weatherapp.BuildConfig
import emperorfin.android.weatherapp.data.remote.WeatherApi
import emperorfin.android.weatherapp.domain.model.WeatherModel
import javax.inject.Inject


class WeatherRepository @Inject constructor(private val api: WeatherApi) {
    suspend fun getWeather(city: String): WeatherModel {
        return api.getWeather(city, BuildConfig.OPEN_WEATHER_MAP_API_KEY)
    }
}
