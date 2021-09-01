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

    private val parameters = "hostname," +
            "pl_letter,pl_controv_flag,pl_rade,pl_orbper," +
            "pl_bmasse,pl_orbeccen,pl_orbincl," +
            "sy_pnum,sy_snum,sy_mnum,cb_flag," +
            "disc_telescope,disc_instrument,disc_facility,disc_refname,disc_pubdate," +
            "disc_locale,discoverymethod,disc_year," +
            "rv_flag,pul_flag,ptv_flag," +
            "tran_flag,ast_flag,obm_flag," +
            "micro_flag,etv_flag,ima_flag,dkin_flag,"+
            "pl_orbper_reflink, pl_orbsmax, pl_orbsmax_reflink," +
            "pl_dens, pl_dens_reflink,pl_bmasse_reflink," +
            "pl_tranmid, pl_tranmid_reflink"


    private val allPlanetsUrl =
        "https://exoplanetarchive.ipac.caltech.edu/TAP/sync?query=select+distinct(pl_name),$parameters%20from+%20pscomppars+order+by+pl_name+asc+&format=json"

    suspend fun getPlanets() = suspendCoroutine<JSONArray> { cont ->
        val jsonArrayRequest = JsonArrayRequest(
            Request.Method.GET,
            allPlanetsUrl,
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