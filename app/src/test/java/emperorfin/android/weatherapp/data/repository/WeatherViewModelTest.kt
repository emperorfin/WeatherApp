package emperorfin.android.weatherapp.data.repository

import emperorfin.android.weatherapp.data.remote.WeatherApi
import emperorfin.android.weatherapp.domain.model.Main
import emperorfin.android.weatherapp.domain.model.Weather
import emperorfin.android.weatherapp.domain.model.WeatherModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class WeatherRepositoryTest {

    private val api = mockk<WeatherApi>()
    private lateinit var repo: WeatherRepository

    @Before
    fun setup() {
        repo = WeatherRepository(api)
    }

    @Test
    fun `getWeather returns correct response`() = runTest {
        val mockResponse = WeatherModel("Lagos", Main(30.0), listOf(Weather("Sunny")))
        coEvery { api.getWeather("Lagos", any(), any()) } returns mockResponse

        val result = repo.getWeather("Lagos")

        assertEquals("Lagos", result.name)
        assertEquals(30.0, result.main.temp, 0.01)
        assertEquals("Sunny", result.weather.first().description)
    }
}
