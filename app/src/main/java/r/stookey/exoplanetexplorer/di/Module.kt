package r.stookey.exoplanetexplorer.di

import android.content.Context
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import r.stookey.exoplanetexplorer.domain.DomainDto
import r.stookey.exoplanetexplorer.domain.DomainDtoImpl
import r.stookey.exoplanetexplorer.network.ExoplanetApiService
import r.stookey.exoplanetexplorer.repository.Repository
import r.stookey.exoplanetexplorer.repository.RepositoryImpl
import timber.log.Timber
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object Module {

    @Singleton
    @Provides
    fun providesDomainDtoImpl(): DomainDto {
        Timber.i("PlanetDto injected")
        return DomainDtoImpl()
    }

    @Singleton
    @Provides
    fun providesQueue(@ApplicationContext appContext: Context): RequestQueue {
        Timber.i("RequestQueue injected")
        return Volley.newRequestQueue(appContext)
    }


    @Singleton
    @Provides
    fun providesApiService(requestQueue: RequestQueue): ExoplanetApiService {
        Timber.i("ExoplanetApiService injected")
        return ExoplanetApiService(requestQueue)
    }


    @Singleton
    @Provides
    fun provideRepository(exoplanetApiService: ExoplanetApiService): Repository {
        Timber.i("RepositoryImpl injected")
        return RepositoryImpl(exoplanetApiService)
    }

}