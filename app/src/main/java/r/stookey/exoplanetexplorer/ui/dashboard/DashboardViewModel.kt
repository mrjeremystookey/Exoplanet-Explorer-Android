package r.stookey.exoplanetexplorer.ui.dashboard

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import r.stookey.exoplanetexplorer.domain.Planet
import r.stookey.exoplanetexplorer.repository.RepositoryImpl
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(private val repo: RepositoryImpl): ViewModel() {

    val planets: MutableState<List<Planet>> = mutableStateOf(listOf())
    val query = mutableStateOf("")

    init {
        Timber.d("dashboardViewModel initialized")
        onViewModelLoad()
    }

    private fun onViewModelLoad(){
        viewModelScope.launch {
            val allPlanets = repo.getAllPlanetsFromCache()
            Timber.d("number of Planets from cache: " + allPlanets.size)
            planets.value = allPlanets
        }
    }

    fun newSearchByPlanetName(query: String) {
        viewModelScope.launch {
            val result = repo.searchPlanetsFromCache(query)
            Timber.d("number of Planets returned from search: ${result.size}")
        }
    }

    fun onQueryChanged(query: String){
        Timber.d("query is changing: $query")
        this.query.value = query
    }

}