package r.stookey.exoplanetexplorer.network

import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonArrayRequest
import org.json.JSONArray
import timber.log.Timber
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine


class ExoplanetApiService @Inject constructor(private var queue: RequestQueue) {

    init {
        Timber.d("ExoplanetApiService init running")
    }

    suspend fun getPlanets() = suspendCoroutine<JSONArray> { cont ->
        val jsonArrayRequest = JsonArrayRequest(
            Request.Method.GET,
            Urls.ALL_PLANETS_URL,
            null,
            { response ->
                Timber.d("It worked")
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
        Timber.d("jsonArrayRequest added to queue")
    }
}