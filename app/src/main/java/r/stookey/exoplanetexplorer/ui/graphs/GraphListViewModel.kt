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
import r.stookey.exoplanetexplorer.domain.Graph
import r.stookey.exoplanetexplorer.domain.Planet
import r.stookey.exoplanetexplorer.repository.RepositoryImpl
import javax.inject.Inject



@HiltViewModel
class GraphListViewModel @Inject constructor() : ViewModel() {

    private val _graphList: MutableState<List<Graph>> = mutableStateOf(listOf())
    val graphList: State<List<Graph>> = _graphList

    init {
        createGraphList()
    }


    private fun createGraphList(){
        val graph = Graph("Detections Per Year")
        val listOfGraphs = mutableListOf<Graph>()
        listOfGraphs.add(graph)
        _graphList.value = listOfGraphs
    }

}