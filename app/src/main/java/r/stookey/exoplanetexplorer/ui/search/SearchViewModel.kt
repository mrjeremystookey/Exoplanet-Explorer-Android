package r.stookey.exoplanetexplorer.ui.search

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
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
    private val _planetsList: MutableState<List<Planet>> = mutableStateOf(listOf())
    val planetsList: State<List<Planet>> = _planetsList


    private val _query = mutableStateOf("")
    val query: State<String> = _query


    private val _loading = mutableStateOf(false)
    val loading: State<Boolean> = _loading


    init {
        Timber.d("SearchViewModel initialized")
        viewModelScope.launch {
            repo.getAllPlanetsFromCache.collect { planets ->
                _planetsList.value = planets
            }
            Timber.d("number of Planets from cache: " + _planetsList.value.size)
        }
    }



    fun onQueryChanged(query: String) {
        Timber.d("query is changing: $query")
        _query.value = query
            viewModelScope.launch {
                repo.searchPlanetsFromCache(query).collect { listOfPlanets ->
                    Timber.d("Number of planets returned from search: ${listOfPlanets.size}")
                    _planetsList.value = listOfPlanets
                }
            }
    }

    fun newSearchByPlanetName(query: String) {
        viewModelScope.launch {
            repo.searchPlanetsFromCache(query).collect { listOfPlanets ->
                Timber.d("number of Planets returned from search: ${listOfPlanets.size}")
                _planetsList.value = listOfPlanets
            }
        }
    }


    fun networkButtonPressed() {
        Timber.i("launching network service for new planets")
        viewModelScope.launch {
            _loading.value = true
            repo.getPlanetsFromNetwork()
            _loading.value = false
        }
    }

    fun clearCacheButtonPressed() {
        Timber.i("clearing local cache")
        viewModelScope.launch {
            repo.removeAllPlanetsFromCache()
        }
    }
}