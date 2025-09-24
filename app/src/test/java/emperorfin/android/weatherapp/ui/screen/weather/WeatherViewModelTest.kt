package emperorfin.android.weatherapp.ui.screen.weather

import emperorfin.android.weatherapp.data.repository.WeatherRepository
import emperorfin.android.weatherapp.domain.model.Main
import emperorfin.android.weatherapp.domain.model.Weather
import emperorfin.android.weatherapp.domain.model.WeatherModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class WeatherViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private val repo = mockk<WeatherRepository>()
    private lateinit var viewModel: WeatherViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher) // Optional now
        viewModel = WeatherViewModel(repo, dispatcher = testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `fetchWeather sets error on failure`() = runTest(testDispatcher) {
        coEvery { repo.getWeather("Lagos") } throws Exception("Network error")

        viewModel.fetchWeather("Lagos")
        advanceUntilIdle()

        assertEquals("Something went wrong.", viewModel.errorMessage.value)
        assertNull(viewModel.weather.value)
        assertFalse(viewModel.loading.value)
    }

    @Test
    fun `fetchWeather sets weather and clears error`() = runTest(testDispatcher) {
        val mockResponse = WeatherModel("Lagos", Main(30.0), listOf(Weather("Sunny")))
        coEvery { repo.getWeather("Lagos") } returns mockResponse

        viewModel.fetchWeather("Lagos")
        advanceUntilIdle()

        assertEquals("Lagos", viewModel.weather.value?.name)
        assertNull(viewModel.errorMessage.value)
        assertFalse(viewModel.loading.value)
    }

}
