package r.stookey.exoplanetexplorer.ui.dashboard

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import r.stookey.exoplanetexplorer.domain.Planet
import r.stookey.exoplanetexplorer.repository.RepositoryImpl
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(private val repo: RepositoryImpl): ViewModel() {

    val planets: MutableState<List<Planet>> = mutableStateOf(listOf())

    init {
        Timber.d("dashboardViewModel initialized")
        viewModelScope.launch {
            val result = repo.getAllPlanetsFromCache()
            Timber.d("number of Planets from cache: " + result.size)
            planets.value = result
        }
    }
}