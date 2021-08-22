package r.stookey.exoplanetexplorer.ui.notifications

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import r.stookey.exoplanetexplorer.repository.RepositoryImpl
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class NotificationsViewModel  @Inject constructor(var repo: RepositoryImpl) : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        Timber.i("setting _text")
        value = "This is notifications Fragment"
    }
    val text: LiveData<String> = _text
}

