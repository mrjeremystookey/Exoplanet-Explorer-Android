package r.stookey.exoplanetexplorer.ui.search

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
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import dagger.hilt.android.AndroidEntryPoint
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
                val planets = searchViewModel.planets.value
                val loading = searchViewModel.loading.value
                ExoplanetExplorerTheme {
                    Scaffold(
                        topBar = {
                            PlanetSearchBar(
                                query = query,
                                onQueryChanged = searchViewModel::onQueryChanged,
                                onPlanetSearched = searchViewModel::newSearchByPlanetName,
                            )
                        },
                        content = {
                            Column {
                                Box(modifier = Modifier
                                    .fillMaxSize()
                                    .padding(top = 4.dp)
                                    .weight(.9f)) {
                                    LazyColumn(
                                        verticalArrangement = Arrangement.spacedBy(4.dp),
                                        contentPadding = PaddingValues(horizontal = 8.dp,
                                            vertical = 4.dp),
                                    )
                                    {
                                        searchViewModel.planetsUsingFlow.observe(
                                            viewLifecycleOwner,
                                            { planets ->
                                                itemsIndexed(items = planets) { index, planet ->
                                                    PlanetCard(planet = planet) {

                                                    }
                                                }
                                            })
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
                    )
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




