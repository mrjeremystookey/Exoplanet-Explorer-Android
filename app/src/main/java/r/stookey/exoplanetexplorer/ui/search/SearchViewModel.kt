package r.stookey.exoplanetexplorer.ui.search

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.WorkManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import r.stookey.exoplanetexplorer.domain.Planet
import r.stookey.exoplanetexplorer.domain.SortStatus
import r.stookey.exoplanetexplorer.repository.RepositoryImpl
import timber.log.Timber
import javax.inject.Inject

sealed class UiState {
    object Loading: UiState()
    object Loaded: UiState()
    object Empty: UiState()
}



@HiltViewModel
class SearchViewModel @Inject constructor(private val repo: RepositoryImpl, private val workManager: WorkManager) : ViewModel() {



    //Mutable State Variables
    private val _planetsList: MutableState<List<Planet>> = mutableStateOf(listOf())
    val planetsList: State<List<Planet>> = _planetsList

    private val _query = mutableStateOf("")
    val query: State<String> = _query

    private val _uiState = MutableLiveData<UiState>()
    val uiState: LiveData<UiState> = _uiState

    private val _cacheState = MutableLiveData<Boolean>()
    val cacheState: LiveData<Boolean> =_cacheState

    private val _ascendingState = MutableLiveData<Boolean>()
    var selectedPlanet: MutableState<Planet> = mutableStateOf(Planet())

    private val _numberAdded: MutableLiveData<Int> = MutableLiveData<Int>()
    val numberAdded: LiveData<Int>
        get() = _numberAdded

    private val _sortList: MutableState<List<SortStatus>> = mutableStateOf(listOf(
        SortStatus.EarthMass,
        SortStatus.EarthRadius,
        SortStatus.JupiterMass,
        SortStatus.JupiterRadius,
        SortStatus.Insolation,
        SortStatus.OrbitalEccentricity,
        SortStatus.EquilibriumTemperature,
        SortStatus.SemiMajorAxis,
        SortStatus.Period,
        SortStatus.Density,))
    val sortList: State<List<SortStatus>> = _sortList




    init {
        Timber.d("SearchViewModel initialized")
        //Gets planets and populates list
        viewModelScope.launch {
            repo.getAllPlanetsFromCache.collect { allPlanets ->
                _planetsList.value = allPlanets
                _uiState.value = UiState.Loaded
                _ascendingState.value = true

                if(allPlanets.isEmpty())
                    _uiState.value = UiState.Empty

            }
            Timber.d("number of Planets from cache: " + _planetsList.value.size)
        }



        //Should be removed when viewModel is shut otherwise leaks
        //Lets ViewModel know when Repo is done caching planets so that SnackBar composable can be shown
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

    fun newSearchByPlanetName(planetName: String){
        viewModelScope.launch {
            repo.searchPlanetsFromCache(planetName).collect { planet ->
                Timber.d("Planet name returned: $planet")
                selectedPlanet.value = planet[0]
            }
        }
    }

    fun onSortClicked(sort: SortStatus){
        Timber.d("Sort button clicked")
        val sortedList: List<Planet> = when(sort){
            SortStatus.EarthMass -> {
                _planetsList.value.sortedBy { it.planetaryMassEarth }.filter { it.planetaryMassEarth != null }
            }
            SortStatus.Period -> {
                _planetsList.value.sortedBy { it.planetaryOrbitPeriod }.filter { it.planetaryOrbitPeriod != null }
            }
            SortStatus.EarthRadius -> {
                _planetsList.value.sortedBy { it.planetaryRadiusEarth }.filter { it.planetaryRadiusEarth != null }
            }
            SortStatus.Density -> {
                _planetsList.value.sortedBy { it.planetDensity }.filter { it.planetDensity != null }
            }
            SortStatus.Insolation -> {
                _planetsList.value.sortedBy { it.planetaryInsolationFlux }.filter { it.planetaryInsolationFlux != null }
            }
            SortStatus.JupiterMass -> {
                _planetsList.value.sortedBy { it.planetaryMassJupiter }.filter { it.planetaryMassJupiter != null }
            }
            SortStatus.JupiterRadius -> {
                _planetsList.value.sortedBy { it.planetaryRadiusJupiter }.filter { it.planetaryRadiusJupiter != null }
            }
            SortStatus.SemiMajorAxis -> {
                _planetsList.value.sortedBy { it.orbitSemiMajorAxis }.filter { it.orbitSemiMajorAxis != null }
            }
            SortStatus.OrbitalEccentricity -> {
                _planetsList.value.sortedBy { it.planetaryOrbitalEccentricity }.filter { it.planetaryOrbitalEccentricity != null }
            }
            SortStatus.EquilibriumTemperature -> {
                _planetsList.value.sortedBy { it.planetaryEquilibriumTemperature }.filter { it.planetaryEquilibriumTemperature != null }
            }

        }
        _ascendingState.value = true
        _planetsList.value = sortedList
    }

    //Flips planet list ascending or descending order
    fun changeAscendingDescending(){
        Timber.d("Flipping sort order")
        if(_ascendingState.value.let { true }){
            val descendingList: List<Planet> = planetsList.value.asReversed()
            _planetsList.value = descendingList
        } else {
            val ascendingList: List<Planet> = planetsList.value
            _planetsList.value = ascendingList
        }
    }


    fun networkButtonPressed() {
        Timber.i("launching network service for new planets")
        viewModelScope.launch {
            runCatching {
                _uiState.value = UiState.Loading
                repo.getPlanetsFromNetwork()
            }.onFailure { error: Throwable ->
                Timber.d("networkButtonPressed failed, $error")
            }.onSuccess {
                Timber.d("networkButtonPressed successful")
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

    override fun onCleared() {
        super.onCleared()
        Timber.d("onCleared called, removing observers")
        repo.doneAdding.removeObserver {}
    }
}