package r.stookey.exoplanetexplorer.ui.search

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import r.stookey.exoplanetexplorer.domain.Planet
import r.stookey.exoplanetexplorer.repository.RepositoryImpl
import timber.log.Timber
import javax.inject.Inject

sealed class UiState {
    object Loading: UiState()
    object Loaded: UiState()
    object Empty: UiState()
    object PlanetsCached: UiState()
}

@HiltViewModel
class SearchViewModel @Inject constructor(private val repo: RepositoryImpl) : ViewModel() {


    //Mutable State Variables
    private val _planetsList: MutableState<List<Planet>> = mutableStateOf(listOf())
    val planetsList: State<List<Planet>> = _planetsList

    private val _query = mutableStateOf("")
    val query: State<String> = _query


    private val _uiState = MutableLiveData<UiState>()
    val uiState: LiveData<UiState>
        get() = _uiState





    init {
        Timber.d("SearchViewModel initialized")

        //Gets planets and populates list
        viewModelScope.launch {
            repo.getAllPlanetsFromCache.collect { planets ->
                _planetsList.value = planets
                _uiState.value = UiState.Loaded
            }
            Timber.d("number of Planets from cache: " + _planetsList.value.size)
        }

        //Lets ViewModel know when Repo is done caching planets so that Snackbar can be shown
        repo.doneAdding.observeForever {
            if(it == true)
                _uiState.value = UiState.PlanetsCached
        }

    }

    fun onQueryChanged(query: String) {
        Timber.d("query is changing: $query")
        _query.value = query
        viewModelScope.launch {
            repo.searchPlanetsFromCache(query).collect { listOfPlanets ->
                _planetsList.value = listOfPlanets
            }
        }
    }

    fun networkButtonPressed() {
        Timber.i("launching network service for new planets")
        viewModelScope.launch {
            runCatching {
                _uiState.value = UiState.Loading
                repo.getPlanetsFromNetwork()
                //TODO examine errors from runCatching
            }
            _uiState.value = UiState.Loaded
        }
    }

    fun clearCacheButtonPressed() {
        Timber.i("clearing local cache")
        viewModelScope.launch {
            repo.removeAllPlanetsFromCache()
            _uiState.value = UiState.Empty
        }
    }

}