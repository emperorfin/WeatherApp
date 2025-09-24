package emperorfin.android.weatherapp.domain.model



data class WeatherModel(
    val name: String,
    val main: Main,
    val weather: List<Weather>
)

data class Main(val temp: Double)
data class Weather(val description: String)
