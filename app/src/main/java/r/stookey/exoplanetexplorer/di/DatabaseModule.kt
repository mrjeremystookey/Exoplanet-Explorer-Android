package r.stookey.exoplanetexplorer.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import r.stookey.exoplanetexplorer.cache.PlanetDao
import r.stookey.exoplanetexplorer.cache.PlanetDatabase
import timber.log.Timber
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): PlanetDatabase {
        Timber.i("Planet Database injected")
        return PlanetDatabase.getInstance(context)
    }

    @Singleton
    @Provides
    fun providePlanetDao(database: PlanetDatabase): PlanetDao {
        Timber.i("Planet Dao created")
        return database.planetDao()
    }

}