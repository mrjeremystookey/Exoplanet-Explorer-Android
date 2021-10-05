package r.stookey.exoplanetexplorer.ui.graphs.detail

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.DataSet
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import r.stookey.exoplanetexplorer.domain.Planet
import r.stookey.exoplanetexplorer.repository.GraphRepository
import r.stookey.exoplanetexplorer.repository.GraphRepositoryImpl
import r.stookey.exoplanetexplorer.repository.RepositoryImpl
import r.stookey.exoplanetexplorer.ui.graphs.plots.DataSetUtil
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class GraphViewModel @Inject constructor(private val repo: GraphRepositoryImpl) : ViewModel() {

    private val _planetsList: MutableState<List<Planet>> = mutableStateOf(listOf())
    val planetsList: State<List<Planet>> = _planetsList

    var selectedData: BarDataSet

    init {
        Timber.d("GraphViewModel initialized")
        getListOfPlanets()
        val discoveryYearDataSet: BarDataSet = DataSetUtil()
            .createDiscoveryYearDataSet(_planetsList.value)
        selectedData = discoveryYearDataSet
    }

    private fun getListOfPlanets() {
        viewModelScope.launch {
            repo.getAllPlanetsFromCache.collect { planets ->
                _planetsList.value = planets }
        }
    }


}
