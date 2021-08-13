package r.stookey.exoplanetexplorer.ui.tester

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TestViewModel : ViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "This is Tester Fragment"
    }
    val text: LiveData<String> = _text
}