package r.stookey.exoplanetexplorer.ui.tester

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import r.stookey.exoplanetexplorer.domain.Planet
import r.stookey.exoplanetexplorer.repository.RepositoryImpl
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class TestViewModel @Inject constructor(private val repo: RepositoryImpl) : ViewModel() {

    private val _text = MutableLiveData<List<Planet>>().apply {}
    val text: LiveData<List<Planet>> = _text

    private val testQuery = "sex"

    init {
        Timber.d("TestViewModel initialized")
        viewModelScope.launch {
            val listFts = repo.searchPlanetsFullText(testQuery)
            val listSearch = repo.searchPlanetsFromCache(testQuery)
            Timber.d("# of results from full text search: ${listFts.size}")
            Timber.d("# of results from regular search: ${listSearch.size}")
        }
    }

}