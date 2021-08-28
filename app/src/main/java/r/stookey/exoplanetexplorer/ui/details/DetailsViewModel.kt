package r.stookey.exoplanetexplorer.ui.details

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import r.stookey.exoplanetexplorer.domain.Planet
import r.stookey.exoplanetexplorer.repository.RepositoryImpl
import timber.log.Timber
import javax.inject.Inject


@HiltViewModel
class DetailsViewModel @Inject constructor(private val repo: RepositoryImpl): ViewModel() {

    var selectedPlanet: MutableState<Planet> = mutableStateOf(Planet())



    init {
        Timber.d("detailsViewModel initialized")
    }

    fun newSearchByPlanetId(planetId: Int) {
        viewModelScope.launch {
            repo.getPlanetFromCache(planetId).collect { planet ->
                Timber.d("Planet name returned: ${planet.planetName}")
                selectedPlanet.value = planet
            }
        }
    }


}
