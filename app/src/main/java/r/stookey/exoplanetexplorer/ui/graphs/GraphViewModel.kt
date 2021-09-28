package r.stookey.exoplanetexplorer.ui.graphs

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
import r.stookey.exoplanetexplorer.ui.search.UiState
import javax.inject.Inject

sealed class GraphUIState {
    object AllGraphs: GraphUIState()
    object GraphSelected: GraphUIState()
    object Empty: GraphUIState()
}



@HiltViewModel
class GraphViewModel @Inject constructor(private val repo: RepositoryImpl) : ViewModel() {


    private val _graphUIState = MutableLiveData<GraphUIState>()
    val graphUIState: LiveData<GraphUIState> = _graphUIState

    private val _planetsList: MutableState<List<Planet>> = mutableStateOf(listOf())
    val planetsList: State<List<Planet>> = _planetsList

    init {
        graphData()
        _graphUIState.value = GraphUIState.AllGraphs
    }

    fun getListOfGraphs(){

    }

    private fun graphData(){
        viewModelScope.launch {
            repo.getAllPlanetsFromCache.collect { planets ->
                _planetsList.value = planets
            }
        }
    }


}