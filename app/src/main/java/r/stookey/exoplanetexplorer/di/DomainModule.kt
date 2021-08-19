package r.stookey.exoplanetexplorer.di

import android.content.Context
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.room.Room
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import r.stookey.exoplanetexplorer.cache.PlanetDatabase
import r.stookey.exoplanetexplorer.domain.PlanetDto
import r.stookey.exoplanetexplorer.domain.PlanetDtoImpl
import r.stookey.exoplanetexplorer.network.ExoplanetApiService
import r.stookey.exoplanetexplorer.repository.Repository
import r.stookey.exoplanetexplorer.repository.RepositoryImpl
import timber.log.Timber
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DomainModule {

    @Singleton
    @Provides
    fun provideMoshi(): Moshi {
        Timber.i("Moshi injected")
        return Moshi.Builder().build()
    }

    @Singleton
    @Provides
    fun providesDomainDtoImpl(): PlanetDto {
        Timber.i("PlanetDto injected")
        return PlanetDtoImpl(provideMoshi())
    }


}