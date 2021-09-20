package r.stookey.exoplanetexplorer.ui.test

import android.content.Context
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.*
import androidx.work.*
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.analytics.ktx.logEvent
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import r.stookey.exoplanetexplorer.ExoplanetApplication
import r.stookey.exoplanetexplorer.domain.Planet
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
class TestViewModel @Inject constructor(private val repo: RepositoryImpl,
                                        private val workManager: WorkManager) : ViewModel() {

    private var firebaseAnalytics: FirebaseAnalytics = Firebase.analytics

    private val _planetsList: MutableState<List<Planet>> = mutableStateOf(listOf())
    val planetsList: State<List<Planet>> = _planetsList

    private val _uiState = MutableLiveData<TestUiState>()
    val uiState: LiveData<TestUiState>
        get() = _uiState

    init {
        Timber.d("TestViewModel initialized")
        _uiState.value = TestUiState.Loaded
        graphData()
    }

    fun logInFireBase(){
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW) {
            param(FirebaseAnalytics.Param.ITEM_ID, 1)
            param(FirebaseAnalytics.Param.ITEM_NAME, "TestViewModel")
        }
    }


    fun doSomeWork(){
        val testPlanetSyncWorkRequest: WorkRequest = OneTimeWorkRequestBuilder<ExoplanetApiWorker>().build()
        Timber.d("one time work request created ${testPlanetSyncWorkRequest.id}")
        workManager.enqueue(testPlanetSyncWorkRequest)
        Timber.d("status of one time work request: ${workManager.getWorkInfoById(testPlanetSyncWorkRequest.id)}")
    }


    fun graphData(){
        viewModelScope.launch {
            repo.getAllPlanetsFromCache.collect { planets ->
                _planetsList.value = planets
            }
        }
    }







}