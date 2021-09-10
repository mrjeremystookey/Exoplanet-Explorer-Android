package r.stookey.exoplanetexplorer.network

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.RequestFuture
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import org.json.JSONArray
import timber.log.Timber


//TODO fix API Worker class
@HiltWorker
class ExoplanetApiWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    var queue: RequestQueue): Worker(appContext, workerParams) {


    override fun doWork(): Result {
        Timber.d("doing some work with WorkManager")
        var future: RequestFuture<JSONArray> = RequestFuture.newFuture()
        var request = JsonArrayRequest(Request.Method.GET, Urls.ALL_PLANETS_URL, null, future, future)
        //requestQueue.add(request)
        /*return try {
            var response = future.get(60, TimeUnit.SECONDS)
            Timber.d("response from background work: ${response[0]}")
            Result.success()
        } catch (throwable: Throwable) {
            Timber.d("response from background work failed")
            return Result.failure()
        }*/
        return Result.success()
    }

    override fun onStopped() {
        Timber.d("ApiWorker stopped")
        super.onStopped()
    }
}