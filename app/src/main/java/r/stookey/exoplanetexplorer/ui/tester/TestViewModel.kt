package r.stookey.exoplanetexplorer.ui.tester

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import r.stookey.exoplanetexplorer.domain.Planet
import r.stookey.exoplanetexplorer.repository.RepositoryImpl
import timber.log.Timber
import javax.inject.Inject

class TestViewModel : ViewModel() {

    private val _text = MutableLiveData<List<Planet>>().apply {}
    val text: LiveData<List<Planet>> = _text



}