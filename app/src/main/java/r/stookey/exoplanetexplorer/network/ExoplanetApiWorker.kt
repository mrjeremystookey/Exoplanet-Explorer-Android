package r.stookey.exoplanetexplorer.network

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.Data
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.RequestFuture
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONArray
import timber.log.Timber


@HiltWorker
class ExoplanetApiWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    var queue: RequestQueue): CoroutineWorker(appContext, workerParams)
{

    override suspend fun doWork(): Result {
        return try {
            withContext(Dispatchers.IO) {
                Timber.d("checking for new Planets in the background")
                val future: RequestFuture<JSONArray> = RequestFuture.newFuture()
                val request = JsonArrayRequest(Request.Method.GET, Urls.ALL_PLANETS_URL, null, future, future)
                queue.add(request)
                val newPlanetJSONArray = future.get()
                var data = workDataOf("NEW_PLANETS_JSON" to newPlanetJSONArray)
                Timber.d("Planets found in background: ${newPlanetJSONArray.length()}")
                Result.success(data)
            }
        } catch (throwable: Throwable){
            Timber.e("${throwable.stackTrace}")
            return Result.failure()
        }

    }




}