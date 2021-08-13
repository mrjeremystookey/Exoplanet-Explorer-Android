package r.stookey.exoplanetexplorer.data

import android.app.Application
import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import androidx.collection.LruCache
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.ImageLoader
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.JsonObject
import org.json.JSONObject


class ExoplanetApiService() {

    private val requestQueue = MySingleton.getInstance(Application())


    fun makeJsonRequest(url: String): JSONObject {
        var returnedObject = JSONObject()
        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null,
            {
                //Handle success
            Log.d("Exoplanet API Service", "number of objects found: ${it.length()}")
                returnedObject = it
            },
            {
                //Handle failure
                Log.d("ExoplanetApiService", "error during jsonRequest")
            }
        )
        requestQueue.addToRequestQueue(jsonObjectRequest)
        return returnedObject
    }


    class MySingleton constructor(context: Context) {
        companion object {
            @Volatile
            private var INSTANCE: MySingleton? = null
            fun getInstance(context: Context) =
                INSTANCE ?: synchronized(this) {
                    INSTANCE ?: MySingleton(context).also {
                        INSTANCE = it
                    }
                }
        }

        val imageLoader: ImageLoader by lazy {
            ImageLoader(requestQueue,
                object : ImageLoader.ImageCache {
                    private val cache = LruCache<String, Bitmap>(20)
                    override fun getBitmap(url: String): Bitmap? {
                        return cache.get(url)
                    }

                    override fun putBitmap(url: String, bitmap: Bitmap) {
                        cache.put(url, bitmap)
                    }
                })
        }
        val requestQueue: RequestQueue by lazy {
            // applicationContext is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.
            Volley.newRequestQueue(context.applicationContext)
        }

        fun <T> addToRequestQueue(req: Request<T>) {
            requestQueue.add(req)
        }
    }
}