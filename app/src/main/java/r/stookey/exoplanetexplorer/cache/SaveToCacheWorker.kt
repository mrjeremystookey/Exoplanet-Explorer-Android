package r.stookey.exoplanetexplorer.cache

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class SaveToCacheWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters): CoroutineWorker(appContext, workerParams){

    override suspend fun doWork(): Result {
        //TODO Convert incoming newPlanetsJsonArray into List of Planet Objects and check to see if they have been cached
        return Result.success()
    }
}