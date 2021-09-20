package r.stookey.exoplanetexplorer.di

import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import r.stookey.exoplanetexplorer.ExoplanetApplication
import r.stookey.exoplanetexplorer.domain.PlanetDto
import r.stookey.exoplanetexplorer.domain.PlanetDtoImpl
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