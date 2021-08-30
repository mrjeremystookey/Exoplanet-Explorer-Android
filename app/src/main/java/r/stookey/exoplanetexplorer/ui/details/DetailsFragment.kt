package r.stookey.exoplanetexplorer.ui.details

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import r.stookey.exoplanetexplorer.ui.compose.PlanetDetails
import timber.log.Timber

@AndroidEntryPoint
class DetailsFragment : Fragment() {


    private lateinit var detailsViewModel: DetailsViewModel
    private val args: DetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        detailsViewModel = ViewModelProvider(this).get(DetailsViewModel::class.java)
        detailsViewModel.newSearchByPlanetId(args.planetID)
        return ComposeView(requireContext()).apply {
            setContent {
                Scaffold(topBar = {
                    TopAppBar {
                    }
                }, content = {
                    PlanetDetails(planet = detailsViewModel.selectedPlanet.value)
                })
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        Timber.d("onActivityCreated called")
        super.onActivityCreated(savedInstanceState)
    }

}