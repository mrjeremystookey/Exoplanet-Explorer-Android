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
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import r.stookey.exoplanetexplorer.domain.Planet
import r.stookey.exoplanetexplorer.repository.RepositoryImpl
import timber.log.Timber
import javax.inject.Inject

sealed class UiState {
    object Loading: UiState()
    object Loaded: UiState()
    object Empty: UiState()
}

@HiltViewModel
class SearchViewModel @Inject constructor(private val repo: RepositoryImpl) : ViewModel() {


    //Mutable State Variables
    private val _planetsList: MutableState<List<Planet>> = mutableStateOf(listOf())
    val planetsList: State<List<Planet>> = _planetsList

    private val _query = mutableStateOf("")
    val query: State<String> = _query

    private val _uiState = MutableLiveData<UiState>()
    val uiState: LiveData<UiState> = _uiState

    private val _cacheState = MutableLiveData<Boolean>()
    val cacheState: LiveData<Boolean> =_cacheState



    init {
        Timber.d("SearchViewModel initialized")
        //Gets planets and populates list
        viewModelScope.launch {
            repo.getAllPlanetsFromCache.collect { planets ->
                _planetsList.value = planets
                _uiState.value = UiState.Loaded
                if(planets.isEmpty()){
                    _uiState.value = UiState.Empty
                }
            }
            Timber.d("number of Planets from cache: " + _planetsList.value.size)
        }

        //Should be removed when viewModel is shut otherwise leaks
        //Lets ViewModel know when Repo is done caching planets so that Snackbar composable can be shown
        repo.doneAdding.observeForever {
            _cacheState.value = it == true
        }
    }

    fun onQueryChanged(query: String) {
        Timber.d("query is changing: $query")
        _query.value = query
        viewModelScope.launch {
            repo.searchPlanetsFromCache(query).collect { listOfPlanets ->
                if(uiState.value == UiState.Loaded){
                    Timber.d("number of planets found: ${listOfPlanets.size}")
                    _planetsList.value = listOfPlanets
                }
            }
        }
    }

    suspend fun onSortClicked(){
        Timber.d("Sort button clicked")
        var list = repo.getAllPlanetsFromCache.toList()
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
        }
    }

}