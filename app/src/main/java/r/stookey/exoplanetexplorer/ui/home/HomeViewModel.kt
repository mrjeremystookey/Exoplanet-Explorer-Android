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


    private val _allPlanets = MutableLiveData<List<Planet>>()
    val allPlanets: LiveData<List<Planet>> = _allPlanets

    init {
        Timber.i("homeViewModel initialized")
    }

    fun buttonPressed(){
        Timber.i("button pressed")
        viewModelScope.launch {
            _allPlanets.value = repo.getAllPlanetsFromNetwork()
        }
    }










}