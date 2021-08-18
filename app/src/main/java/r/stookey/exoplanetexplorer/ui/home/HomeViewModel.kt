package r.stookey.exoplanetexplorer.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONObject
import r.stookey.exoplanetexplorer.repository.RepositoryImpl
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repo: RepositoryImpl) : ViewModel() {


    private val _allPlanets = MutableLiveData<JSONArray>()
    val allPlanets: LiveData<JSONArray> = _allPlanets


    init {
        Timber.i("homeViewModel initialized")
    }

    fun buttonPressed(){
        Timber.i("button pressed")
        viewModelScope.launch {
            _allPlanets.value = repo.getAllPlanets()
        }
    }










}