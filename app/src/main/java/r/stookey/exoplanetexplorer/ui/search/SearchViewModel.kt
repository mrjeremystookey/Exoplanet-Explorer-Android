package r.stookey.exoplanetexplorer.ui.search

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import r.stookey.exoplanetexplorer.domain.Planet
import r.stookey.exoplanetexplorer.repository.RepositoryImpl
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val repo: RepositoryImpl) : ViewModel() {


    //Mutable State Variables
    val planetsList: MutableState<List<Planet>> = mutableStateOf(listOf())
    val query = mutableStateOf("")
    val loading = mutableStateOf(false)


    init {
        Timber.d("dashboardViewModel initialized")
        viewModelScope.launch {
            repo.getAllPlanetsFromCache.collect { planets ->
                planetsList.value = planets
            }
            Timber.d("number of Planets from cache: " + planetsList.value.size)
        }
    }

    fun onQueryChanged(query: String) {
        Timber.d("query is changing: $query")
        this.query.value = query
        viewModelScope.launch {
             repo.searchPlanetsFromCache(query).collect { listOfPlanets ->
                 Timber.d("Number of planets returned from search: ${listOfPlanets.size}")
                 planetsList.value = listOfPlanets
             }
        }
    }

    fun newSearchByPlanetName(query: String) {
        viewModelScope.launch {
            repo.searchPlanetsFromCache(query).collect { listOfPlanets ->
                Timber.d("number of Planets returned from search: ${listOfPlanets.size}")
                planetsList.value = listOfPlanets
            }
        }
    }


    fun networkButtonPressed() {
        Timber.i("launching network service for new planets")
        viewModelScope.launch {
            loading.value = true
            repo.getPlanetsFromNetwork()
            loading.value = false
        }
    }

    fun clearCacheButtonPressed() {
        Timber.i("clearing local cache")
        viewModelScope.launch {
            repo.removeAllPlanetsFromCache()
        }
    }
}