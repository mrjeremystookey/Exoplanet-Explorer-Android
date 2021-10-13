package r.stookey.exoplanetexplorer

import android.app.Application
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.github.mikephil.charting.utils.Utils
import dagger.hilt.android.HiltAndroidApp
import r.stookey.exoplanetexplorer.cache.ExoplanetCacheUpdateWorker
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject


@HiltAndroidApp
class ExoplanetApplication: Application(), Configuration.Provider {

    @Inject lateinit var workerFactory: HiltWorkerFactory
    @Inject lateinit var workManager: WorkManager

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        Timber.d("Timber tree planted")
        Utils.init(applicationContext)
        periodicBackgroundWork()
    }

    //Syncs planet list on startup and then every week
    private fun periodicBackgroundWork(){
        Timber.d("Starting background planet sync")
        val planetSyncWorkRequest = PeriodicWorkRequestBuilder<ExoplanetCacheUpdateWorker>(7, TimeUnit.DAYS)
            .build()
        workManager.enqueue(planetSyncWorkRequest)
    }



    override fun getWorkManagerConfiguration() = Configuration.Builder()
        .setWorkerFactory(workerFactory)
        .setMinimumLoggingLevel(android.util.Log.INFO)
        .build()

}
