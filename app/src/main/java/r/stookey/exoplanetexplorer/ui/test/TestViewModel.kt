package r.stookey.exoplanetexplorer.ui.test

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkRequest
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.analytics.ktx.logEvent
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import r.stookey.exoplanetexplorer.network.ExoplanetApiWorker
import r.stookey.exoplanetexplorer.repository.RepositoryImpl
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject


sealed class TestUiState {
    object Loading: TestUiState()
    object Loaded: TestUiState()
    object Empty: TestUiState()
}

@HiltViewModel
class TestViewModel @Inject constructor(private val repo: RepositoryImpl) : ViewModel() {

    private var firebaseAnalytics: FirebaseAnalytics = Firebase.analytics
    //private var workManager: WorkManager = WorkManager.initialize()



    private val _uiState = MutableLiveData<TestUiState>()
    val uiState: LiveData<TestUiState>
        get() = _uiState

    init {
        Timber.d("TestViewModel initialized")
        _uiState.value = TestUiState.Loaded
    }

    fun logInFireBase(){
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW) {
            param(FirebaseAnalytics.Param.ITEM_ID, 1)
            param(FirebaseAnalytics.Param.ITEM_NAME, "TestViewModel")
        }
    }


    fun doSomeWork(){
        val planetSyncWorkRequest: WorkRequest = PeriodicWorkRequestBuilder<ExoplanetApiWorker>(1, TimeUnit.DAYS).build()

    }







}