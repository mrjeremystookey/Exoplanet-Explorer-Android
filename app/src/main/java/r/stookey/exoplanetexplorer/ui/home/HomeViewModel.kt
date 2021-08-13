package r.stookey.exoplanetexplorer.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import r.stookey.exoplanetexplorer.data.ExoplanetApiService
import r.stookey.exoplanetexplorer.repository.RepositoryImpl


class HomeViewModel() : ViewModel() {

    //private val repositoryImpl = RepositoryImpl()
    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text

    //Should be a coroutine
    /*suspend fun getAllPlanets(): JSONObject {
        var allPlanets = JSONObject()
        withContext(Dispatchers.IO){
            allPlanets = repositoryImpl.getAllPlanets()
        }
        return allPlanets
    }*/

}