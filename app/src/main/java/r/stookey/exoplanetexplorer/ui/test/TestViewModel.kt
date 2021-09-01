package r.stookey.exoplanetexplorer.ui.test

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.analytics.ktx.logEvent
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import r.stookey.exoplanetexplorer.repository.RepositoryImpl
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class TestViewModel @Inject constructor(private val repo: RepositoryImpl) : ViewModel() {

    private var firebaseAnalytics: FirebaseAnalytics = Firebase.analytics

    init {
        Timber.d("TestViewModel initialized")
    }

    fun logInFireBase(){
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW) {
            param(FirebaseAnalytics.Param.ITEM_ID, 1)
            param(FirebaseAnalytics.Param.ITEM_NAME, "TestViewModel")
        }
    }




}