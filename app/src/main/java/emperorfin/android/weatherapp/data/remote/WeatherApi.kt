package emperorfin.android.weatherapp.data.remote

import emperorfin.android.weatherapp.domain.model.WeatherModel
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    @GET("weather")
    suspend fun getWeather(
        @Query("q") city: String,
        @Query("appid") apiKey: String,
        @Query("units") units: String = "metric"
    ): WeatherModel
}