package emperorfin.android.weatherapp.di


import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import emperorfin.android.weatherapp.data.local.Preferences
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalModule {

    @Provides
    @Singleton
    fun providePreference(@ApplicationContext context: Context): Preferences {
        return Preferences(context)
    }
}