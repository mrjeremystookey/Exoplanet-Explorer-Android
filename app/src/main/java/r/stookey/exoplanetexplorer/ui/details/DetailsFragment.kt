package r.stookey.exoplanetexplorer.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Row
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import r.stookey.exoplanetexplorer.ui.compose.PlanetDetails
import r.stookey.exoplanetexplorer.ui.compose.theme.ExoplanetExplorerTheme

@AndroidEntryPoint
class DetailsFragment : Fragment() {

    private val detailsViewModel: DetailsViewModel by viewModels()
    private val args: DetailsFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        detailsViewModel.newSearchByPlanetName(args.planetName)
        return ComposeView(requireContext()).apply {
            setContent {
                ExoplanetExplorerTheme {
                    Scaffold(topBar = {
                        TopAppBar(
                            content = {Text("${detailsViewModel.selectedPlanet.value.planetName}")} ,
                            elevation = 8.dp)

                    }, backgroundColor = MaterialTheme.colors.primaryVariant,
                        content = {
                            PlanetDetails(planet = detailsViewModel.selectedPlanet.value)
                        })
                }
            }
        }
    }


}