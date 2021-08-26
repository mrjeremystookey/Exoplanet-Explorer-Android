package r.stookey.exoplanetexplorer.ui.tester

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.core.view.forEach
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import dagger.hilt.android.AndroidEntryPoint
import r.stookey.exoplanetexplorer.databinding.FragmentTestBinding
import r.stookey.exoplanetexplorer.domain.Planet
import r.stookey.exoplanetexplorer.ui.compose.CircularIndeterminateProgressBar
import r.stookey.exoplanetexplorer.ui.compose.PlanetCard
import r.stookey.exoplanetexplorer.ui.compose.PlanetSearchBar
import r.stookey.exoplanetexplorer.ui.compose.theme.ExoplanetExplorerTheme
import timber.log.Timber

@AndroidEntryPoint
class TestFragment : Fragment() {


    private lateinit var testerViewModel: TesterViewModel
    private var _binding: FragmentTestBinding? = null


    @ExperimentalComposeUiApi
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        testerViewModel = ViewModelProvider(this).get(TesterViewModel::class.java)
        _binding = FragmentTestBinding.inflate(inflater, container, false)
        return ComposeView(requireContext()).apply {
            setContent {
                LazyColumn(){
                    val listOfPlanetsFromFlow = testerViewModel.planetsUsingFlow.observe(viewLifecycleOwner, { planets ->
                        Timber.d("Number of Planets in Flow: ${planets.size}")
                        itemsIndexed(planets){ index, planet ->
                            PlanetCard(
                                planet = planet,
                                onClick = {
                                    Timber.d("planet ${planet.planetName} clicked")
                                }
                            )
                        }
                    })
                }



            }
        }
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        
    }

}