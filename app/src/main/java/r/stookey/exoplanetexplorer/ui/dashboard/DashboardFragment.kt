package r.stookey.exoplanetexplorer.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import dagger.hilt.android.AndroidEntryPoint
import r.stookey.exoplanetexplorer.databinding.FragmentDashboardBinding
import r.stookey.exoplanetexplorer.ui.compose.PlanetCard
import r.stookey.exoplanetexplorer.ui.compose.PlanetSearchBar
import timber.log.Timber

@AndroidEntryPoint
class DashboardFragment : Fragment() {

    private lateinit var dashboardViewModel: DashboardViewModel
    private var _binding: FragmentDashboardBinding? = null
    @ExperimentalComposeUiApi
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        Timber.i("onCreateView called")
        dashboardViewModel = ViewModelProvider(this).get(DashboardViewModel::class.java)
        val query = dashboardViewModel.query
        return ComposeView(requireContext()).apply {
            setContent {
                val planets = dashboardViewModel.planets.value
                Column {
                    PlanetSearchBar(query, dashboardViewModel)
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        contentPadding = PaddingValues(vertical = 16.dp, horizontal = 16.dp)
                    )
                    {
                        itemsIndexed(items = planets) { index, planet ->
                            PlanetCard(planet = planet, onClick = {
                                Timber.d("planet ${planet.planetName} clicked")
                            })
                        }
                    }
                }
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        Timber.i("onDestroyView called")
        _binding = null
    }

    override fun onStart() {
        super.onStart()
        Timber.i("onStart called")
    }
}




