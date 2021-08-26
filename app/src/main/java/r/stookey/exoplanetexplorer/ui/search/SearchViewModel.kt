package r.stookey.exoplanetexplorer.ui.search

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import r.stookey.exoplanetexplorer.domain.Planet
import r.stookey.exoplanetexplorer.repository.RepositoryImpl
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val repo: RepositoryImpl): ViewModel() {

    private val allPlanetsUrl = "https://exoplanetarchive.ipac.caltech.edu/TAP/sync?query=select+distinct(pl_name),hostname,pl_letter,pl_rade," +
            "pl_orbper,pl_bmasse,sy_pnum,sy_snum,disc_telescope,disc_instrument,disc_facility,disc_locale,discoverymethod,disc_year" +
            "+from+%20pscomppars+order+by+pl_name+desc+&format=json"


    val planets: MutableState<List<Planet>> = mutableStateOf(listOf())
    val query = mutableStateOf("")
    val loading = mutableStateOf(false)


    //Flow implementation
    val planetsUsingFlow: LiveData<List<Planet>> = repo.planetFlow.asLiveData()


    init {
        Timber.d("dashboardViewModel initialized")
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

    fun networkButtonPressed(){
        Timber.d("isLoading: ${loading.value}")
        Timber.i("getting planets from query string")
        viewModelScope.launch {
            loading.value = true
            repo.getPlanetsFromNetwork(allPlanetsUrl)
            loading.value = false
            Timber.d("isLoading: ${loading.value}")
        }
    }

    fun clearCacheButtonPressed() {
        Timber.i("clearing local cache")
        viewModelScope.launch {
            repo.removeAllPlanetsFromCache()
        }
    }
}