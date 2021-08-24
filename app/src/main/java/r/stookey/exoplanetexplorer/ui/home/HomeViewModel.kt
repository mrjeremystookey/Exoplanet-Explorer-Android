package r.stookey.exoplanetexplorer.ui.home

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
class HomeViewModel @Inject constructor(private val repo: RepositoryImpl) : ViewModel() {

    private val allPlanetsUrl = "https://exoplanetarchive.ipac.caltech.edu/TAP/sync?query=select+distinct(pl_name),hostname,pl_letter,pl_rade," +
            "pl_orbper,pl_bmasse,sy_pnum,sy_snum,disc_telescope,disc_instrument,disc_facility,disc_locale,discoverymethod,disc_year" +
            "+from+%20pscomppars+order+by+pl_name+desc+&format=json"

    private val _allPlanets = MutableLiveData<List<Planet>>()
    val allPlanets: LiveData<List<Planet>> = _allPlanets

    init {
        Timber.i("homeViewModel initialized")
    }

    fun networkButtonPressed(){
        Timber.i("getting planets from query string")
        viewModelScope.launch {
            repo.getPlanetsFromNetwork(allPlanetsUrl)
        }
    }

    /*fun pagedNetworkButtonPressed(){
        Timber.d("getting allPlanets paged search results")
        repo.getPagedSearchResults(allPlanetsUrl)
    }*/


    fun cacheButtonPressed(){
        Timber.i("get Planet objects from cache")
        viewModelScope.launch {
            _allPlanets.value = repo.getAllPlanetsFromCache()
        }
    }

    fun clearCacheButtonPressed() {
        Timber.i("clearing local cache")
        viewModelScope.launch {
            repo.removeAllPlanetsFromCache()
        }
    }
}