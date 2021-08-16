package r.stookey.exoplanetexplorer.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import r.stookey.exoplanetexplorer.repository.RepositoryImpl
import timber.log.Timber
import javax.inject.Inject


class HomeViewModel() : ViewModel() {


    @Inject lateinit var repo: RepositoryImpl


    private val _allPlanets = MutableLiveData<JSONObject>()
    val allPlanets: LiveData<JSONObject> = _allPlanets


    init {
        Timber.i("homeViewModel initialized")
        //Timber.d("repo.toString() $repo")
        viewModelScope.launch {
           //_allPlanets.value = repo.getAllPlanets()
        }
    }









}