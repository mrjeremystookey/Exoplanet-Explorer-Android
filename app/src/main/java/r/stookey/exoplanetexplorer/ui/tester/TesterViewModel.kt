package r.stookey.exoplanetexplorer.ui.tester

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import r.stookey.exoplanetexplorer.domain.Planet
import r.stookey.exoplanetexplorer.repository.RepositoryImpl
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class TesterViewModel @Inject constructor(private val repo: RepositoryImpl) : ViewModel() {

    private val allPlanetsUrl = "https://exoplanetarchive.ipac.caltech.edu/TAP/sync?query=select+distinct(pl_name),hostname,pl_letter,pl_rade," +
            "pl_orbper,pl_bmasse,sy_pnum,sy_snum,disc_telescope,disc_instrument,disc_facility,disc_locale,discoverymethod,disc_year" +
            "+from+%20pscomppars+order+by+pl_name+desc+&format=json"

    private val testQuery = "sex"


    val planetsUsingFlow: LiveData<List<Planet>> = repo.planetFlow.asLiveData()
    val query = mutableStateOf("")

    init {
        Timber.d("TestViewModel initialized")
        viewModelScope.launch {
            val listFts = repo.searchPlanetsFullText(testQuery)
            val listSearch = repo.searchPlanetsFromCache(testQuery)
            Timber.d("# of results from full text search: ${listFts.size}")
            Timber.d("# of results from cache search: ${listSearch.size}")
        }
    }








}