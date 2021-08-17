package r.stookey.exoplanetexplorer.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.json.JSONObject
import r.stookey.exoplanetexplorer.repository.RepositoryImpl
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repo: RepositoryImpl) : ViewModel() {


    private val _allPlanets = MutableLiveData<JSONObject>()
    val allPlanets: LiveData<JSONObject> = _allPlanets


    init {
        Timber.i("homeViewModel initialized")
    }

    fun buttonPressed(){
        Timber.i("button pressed")
        viewModelScope.launch {
            val allPlanets = repo.getAllPlanets()
            _allPlanets.value = allPlanets
        }
    }










}