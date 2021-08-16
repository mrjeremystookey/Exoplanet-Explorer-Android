package r.stookey.exoplanetexplorer.ui.notifications

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import timber.log.Timber

class NotificationsViewModel : ViewModel() {



    private val _text = MutableLiveData<String>().apply {
        Timber.i("setting _text")
        value = "This is notifications Fragment"
    }
    val text: LiveData<String> = _text
}