package r.stookey.exoplanetexplorer.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import r.stookey.exoplanetexplorer.R
import r.stookey.exoplanetexplorer.databinding.FragmentSearchBinding
import r.stookey.exoplanetexplorer.ui.compose.CircularIndeterminateProgressBar
import r.stookey.exoplanetexplorer.ui.compose.PlanetCard
import r.stookey.exoplanetexplorer.ui.compose.PlanetSearchBar
import r.stookey.exoplanetexplorer.ui.compose.theme.ExoplanetExplorerTheme
import timber.log.Timber

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private lateinit var searchViewModel: SearchViewModel
    private var _binding: FragmentSearchBinding? = null

    @ExperimentalComposeUiApi
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        Timber.i("onCreateView called")
        searchViewModel = ViewModelProvider(this).get(SearchViewModel::class.java)
        return ComposeView(requireContext()).apply {
            setContent {
                val query = searchViewModel.query
                val isLoading = searchViewModel.loading.value

                ExoplanetExplorerTheme {
                    Scaffold(
                        topBar = {
                            PlanetSearchBar(
                                query = query,
                                onQueryChanged = searchViewModel::onQueryChanged,
                                onPlanetSearched = searchViewModel::newSearchByPlanetName)
                        },
                        content = {
                            PlanetListAndLoading(loading = isLoading)   //Gets changed when a planet is selected
                        }
                    )
                }
            }
        }
    }

    @Composable
    fun PlanetListAndLoading(loading: Boolean){  //Loaded when app is opened
        val listState = rememberLazyListState()
        Column {
            Box(modifier = Modifier
                .fillMaxSize()
                .padding(top = 4.dp)
                .weight(.9f)) {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                    contentPadding = PaddingValues(
                        horizontal = 16.dp,
                        vertical = 4.dp),
                    state = listState
                )
                {
                    items(items = searchViewModel.planetsList.value){ planet ->
                        PlanetCard(
                            planet = planet,
                            navigateToPlanet = {
                                val action = SearchFragmentDirections.viewPlanet(planet.planetID!!)
                                Timber.d("navigating to Planet: ${planet.planetName} with PlanetID: ${planet.planetID}")
                                findNavController().navigate(action)
                            }
                        )
                    }
                }
                CircularIndeterminateProgressBar(isDisplayed = loading)
            }
            Row(modifier = Modifier
                .fillMaxWidth()
                .weight(.1f),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically)
            {
                Button(
                    modifier = Modifier.padding(4.dp),
                    onClick = searchViewModel::networkButtonPressed
                ) {
                    Text("Network")
                }
                Button(
                    modifier = Modifier.padding(4.dp),
                    onClick = searchViewModel::clearCacheButtonPressed,
                ) {
                    Text("Clear Cache")
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




