package emperorfin.android.weatherapp.data.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.preferencesOf
import androidx.datastore.preferences.core.stringPreferencesKey
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import androidx. datastore. preferences. core.Preferences as PreferencesAdx

class PreferencesTest {

    private val mockDataStore = mockk<DataStore<PreferencesAdx>>()
    private val CITY_KEY = stringPreferencesKey("city")

    @Test
    fun `cityFlow emits correct value`() = runTest {
        every { mockDataStore.data } returns flowOf(preferencesOf(CITY_KEY to "Lagos"))

        val cityPref = Preferences(mockk(), mockDataStore)
        val result = cityPref.cityFlow.first()

        assertEquals("Lagos", result)
    }

}