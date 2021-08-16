package r.stookey.exoplanetexplorer.data

import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import org.json.JSONObject
import timber.log.Timber
import javax.inject.Inject


class ExoplanetApiService @Inject constructor(var queue: RequestQueue) {

    init {
        Timber.i("ExoplanetApiService init running")
    }

    fun getAllPlanets(): JSONObject {
        val allPlanetsUrl = "https://exoplanetarchive.ipac.caltech.edu/TAP/sync?query=select+*+from+ps&format=json"
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET,
            allPlanetsUrl,
            null,
            Response.Listener {  },
            Response.ErrorListener {  })
        queue.add(jsonObjectRequest)
        Timber.i("jsonObjectRequest added to queue")
        return JSONObject()
    }


}