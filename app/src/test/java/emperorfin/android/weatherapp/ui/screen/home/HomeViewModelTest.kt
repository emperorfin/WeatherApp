package emperorfin.android.weatherapp.ui.screen.home

import emperorfin.android.weatherapp.data.local.Preferences
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private val mockPrefs = mockk<Preferences>()
    private lateinit var viewModel: HomeViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        coEvery { mockPrefs.cityFlow } returns flowOf("Lagos")
        viewModel = HomeViewModel(mockPrefs, dispatcher = testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `initial city is loaded from preference`() = runTest(testDispatcher) {
        advanceUntilIdle()
        assertEquals("Lagos", viewModel.city.value)
    }

    @Test
    fun `updateCity changes city value`() {
        viewModel.updateCity("Abuja")
        assertEquals("Abuja", viewModel.city.value)
    }

    @Test
    fun `saveCity calls Preferences`() = runTest(testDispatcher) {
        coEvery { mockPrefs.saveCity("Abuja") } returns Unit
        viewModel.updateCity("Abuja")
        viewModel.saveCity()
        advanceUntilIdle()
        coVerify { mockPrefs.saveCity("Abuja") }
    }
}