package r.stookey.exoplanetexplorer.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import r.stookey.exoplanetexplorer.ExoplanetApplication
import timber.log.Timber
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object ApplicationModule {

    @Singleton
    @Provides
    fun providesApplication(): ExoplanetApplication {
        Timber.i("Exoplanet Application Created")
        return ExoplanetApplication()
    }
}