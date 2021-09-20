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
import java.util.concurrent.TimeUnit


@HiltWorker
class ExoplanetApiWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    var queue: RequestQueue): Worker(appContext, workerParams) {


    override fun doWork(): Result {
        Timber.d("doing some work with WorkManager")
        var future: RequestFuture<JSONArray> = RequestFuture.newFuture()
        var request = JsonArrayRequest(Request.Method.GET, Urls.ALL_PLANETS_URL, null, future, future)
        queue.add(request)
        return try {
            var response = future.get(120, TimeUnit.SECONDS)
            //TODO Convert to Planet objects, check if in cache and insert if not
            Timber.d("future response: ${response[1]}")
            Result.success()
        } catch (throwable: Throwable){
            Timber.d("unable to do future work: ${throwable.cause}")
            return Result.failure()
        }
    }

    override fun onStopped() {
        Timber.d("ApiWorker stopped")
        super.onStopped()
    }
}