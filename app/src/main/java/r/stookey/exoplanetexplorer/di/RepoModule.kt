package r.stookey.exoplanetexplorer.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import r.stookey.exoplanetexplorer.cache.PlanetDatabase
import r.stookey.exoplanetexplorer.domain.PlanetDtoImpl
import r.stookey.exoplanetexplorer.network.ExoplanetApiService
import r.stookey.exoplanetexplorer.repository.Repository
import r.stookey.exoplanetexplorer.repository.RepositoryImpl
import timber.log.Timber
import javax.inject.Singleton

//@InstallIn(FragmentComponent::class)
@InstallIn(SingletonComponent::class)
@Module
object RepoModule {



    @Singleton
    @Provides
    fun provideRepository(exoplanetApiService: ExoplanetApiService,
                          planetMapper: PlanetDtoImpl,
                          planetDatabase: PlanetDatabase
    ): Repository {
        Timber.i("RepositoryImpl injected")
        return RepositoryImpl(exoplanetApiService, planetMapper, planetDatabase)
    }
}