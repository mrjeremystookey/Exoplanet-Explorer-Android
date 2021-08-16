package r.stookey.exoplanetexplorer.ui.dashboard

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import r.stookey.exoplanetexplorer.repository.RepositoryImpl
import timber.log.Timber
import javax.inject.Inject

class DashboardViewModel : ViewModel() {

    init {
        Timber.d("dashboardViewModel initialized")
    }

    private val _text = MutableLiveData<String>().apply {
        value = "This is dashboard Fragment"
        Timber.d("setting value for text")
        Log.d("dashboardViewModel", "setting value for text")
    }
    val text: LiveData<String> = _text
}