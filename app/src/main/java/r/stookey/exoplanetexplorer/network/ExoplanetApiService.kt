package r.stookey.exoplanetexplorer.network

import com.android.volley.Request
import com.android.volley.RequestQueue
import org.json.JSONObject
import timber.log.Timber
import javax.inject.Inject

import com.android.volley.DefaultRetryPolicy
import com.android.volley.toolbox.JsonArrayRequest
import org.json.JSONArray
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine


class ExoplanetApiService @Inject constructor(var queue: RequestQueue) {

    init {
        Timber.i("ExoplanetApiService init running")
    }


    suspend fun getAllPlanets() = suspendCoroutine<JSONArray> { cont ->
        val keplerUrl = "https://exoplanetarchive.ipac.caltech.edu/TAP/sync?query=select+*+from+ps+where+pl_name+=+%27Kepler-11%20c%27&format=json"
        val jsonArrayRequest = JsonArrayRequest(
            Request.Method.GET,
            keplerUrl,
            null,
            { response ->
                Timber.d("It worked")
                //Timber.d("API Response: $response")
                cont.resume(response)
            },
            {
                Timber.d("It didn't work")
                Timber.d(it.stackTraceToString())
            })
        jsonArrayRequest.retryPolicy = DefaultRetryPolicy(
            20 * 1000,
            5,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        )
        queue.add(jsonArrayRequest)
        Timber.i("jsonArrayRequest added to queue")
    }

}