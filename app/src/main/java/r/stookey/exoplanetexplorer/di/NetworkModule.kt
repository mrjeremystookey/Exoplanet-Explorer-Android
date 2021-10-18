package r.stookey.exoplanetexplorer.di

import android.content.Context
import androidx.work.WorkManager
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import r.stookey.exoplanetexplorer.network.ExoplanetApiService
import timber.log.Timber
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {

    @Singleton
    @Provides
    fun providesApiService(requestQueue: RequestQueue): ExoplanetApiService {
        Timber.d("ExoplanetApiService injected")
        return ExoplanetApiService(requestQueue)
    }

    @Singleton
    @Provides
    fun providesQueue(@ApplicationContext appContext: Context): RequestQueue {
        Timber.d("RequestQueue injected")
        return Volley.newRequestQueue(appContext)
    }

    @Singleton
    @Provides
    fun providesWorkManager(@ApplicationContext appContext: Context): WorkManager {
        Timber.d("WorkManager injected")
        return WorkManager.getInstance(appContext)
    }

}