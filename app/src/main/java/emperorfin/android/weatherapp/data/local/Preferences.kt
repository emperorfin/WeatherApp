package emperorfin.android.weatherapp.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import androidx. datastore. preferences. core.Preferences as PreferencesAdx


val Context.dataStore by preferencesDataStore(name = "settings")

class Preferences(private val context: Context, private val dataStore: DataStore<PreferencesAdx> = context.dataStore) {
    private val CITY_KEY = stringPreferencesKey("city")

    val cityFlow: Flow<String> = dataStore.data.map { prefs ->
        prefs[CITY_KEY] ?: ""
    }

    suspend fun saveCity(city: String) {
        dataStore.edit { prefs ->
            prefs[CITY_KEY] = city
        }
    }
}