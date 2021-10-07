package r.stookey.exoplanetexplorer.ui.test

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkRequest
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.analytics.ktx.logEvent
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import r.stookey.exoplanetexplorer.network.ExoplanetApiWorker
import r.stookey.exoplanetexplorer.repository.RepositoryImpl
import timber.log.Timber
import javax.inject.Inject


@HiltViewModel
class TestViewModel @Inject constructor(private val repo: RepositoryImpl,
                                        private val workManager: WorkManager) : ViewModel() {

    private var firebaseAnalytics: FirebaseAnalytics = Firebase.analytics
    private val _query = mutableStateOf("")
    val query: State<String> = _query

    init {
        Timber.d("TestViewModel initialized")
    }

    fun logInFireBase(){
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW) {
            param(FirebaseAnalytics.Param.ITEM_ID, 1)
            param(FirebaseAnalytics.Param.ITEM_NAME, "TestViewModel")
        }
    }

    fun crash(): Exception {
        throw RuntimeException("Test Crash")
    }


    fun doSomeWork(){
        val testPlanetSyncWorkRequest: WorkRequest = OneTimeWorkRequestBuilder<ExoplanetApiWorker>()
            .addTag("CHECK_NEW_PLANETS")
            .build()
        Timber.d("one time work request created ${testPlanetSyncWorkRequest.id}")
        Timber.d("status of one time work request: ${workManager.getWorkInfoById(testPlanetSyncWorkRequest.id)}")
    }










}