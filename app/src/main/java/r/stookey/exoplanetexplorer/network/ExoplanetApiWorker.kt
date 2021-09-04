package r.stookey.exoplanetexplorer.network

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.RequestFuture
import org.json.JSONArray
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject


//TODO fix API Worker class
class ExoplanetApiWorker @Inject constructor(appContext: Context, workerParams: WorkerParameters, var requestQueue: RequestQueue): Worker(appContext, workerParams) {

    /**
     * Override this method to do your actual background processing.  This method is called on a
     * background thread - you are required to **synchronously** do your work and return the
     * [androidx.work.ListenableWorker.Result] from this method.  Once you return from this
     * method, the Worker is considered to have finished what its doing and will be destroyed.  If
     * you need to do your work asynchronously on a thread of your own choice, see
     * [ListenableWorker].
     *
     *
     * A Worker has a well defined
     * [execution window](https://d.android.com/reference/android/app/job/JobScheduler)
     * to finish its execution and return a [androidx.work.ListenableWorker.Result].  After
     * this time has expired, the Worker will be signalled to stop.
     *
     * @return The [androidx.work.ListenableWorker.Result] of the computation; note that
     * dependent work will not execute if you use
     * [androidx.work.ListenableWorker.Result.failure] or
     * [androidx.work.ListenableWorker.Result.failure]
     */
    override fun doWork(): Result {
        Timber.d("doing some work with WorkManager")
        var future: RequestFuture<JSONArray> = RequestFuture.newFuture()
        var request = JsonArrayRequest(Request.Method.GET, Urls.ALL_PLANETS_URL, null, future, future)
        requestQueue.add(request)
        return try {
            var response = future.get(60, TimeUnit.SECONDS)
            Timber.d("response from background work: ${response[0]}")
            Result.success()
        } catch (throwable: Throwable) {
            Timber.d("response from background work failed")
            return Result.failure()
        }
    }

    /**
     * This method is invoked when this Worker has been told to stop.  At this point, the
     * [ListenableFuture] returned by the instance of [.startWork] is
     * also cancelled.  This could happen due to an explicit cancellation signal by the user, or
     * because the system has decided to preempt the task.  In these cases, the results of the
     * work will be ignored by WorkManager.  All processing in this method should be lightweight
     * - there are no contractual guarantees about which thread will invoke this call, so this
     * should not be a long-running or blocking operation.
     */
    override fun onStopped() {
        Timber.d("ApiWorker stopped")
        super.onStopped()
    }
}