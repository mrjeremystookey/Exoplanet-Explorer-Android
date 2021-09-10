package r.stookey.exoplanetexplorer.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import r.stookey.exoplanetexplorer.databinding.FragmentSearchBinding
import r.stookey.exoplanetexplorer.ui.compose.*
import r.stookey.exoplanetexplorer.ui.compose.theme.ExoplanetExplorerTheme
import timber.log.Timber

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private val searchViewModel: SearchViewModel by viewModels()
    private var _binding: FragmentSearchBinding? = null
    private lateinit var scaffoldState: ScaffoldState
    private lateinit var query: State<String>




    override fun onDestroyView() {
        super.onDestroyView()
        Timber.i("onDestroyView called")
        _binding = null
    }
    override fun onStart() {
        super.onStart()
        Timber.i("onStart called")
    }



    @ExperimentalComposeUiApi
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Timber.i("onCreateView called")
        return ComposeView(requireContext()).apply {
            setContent {
                query = searchViewModel.query
                scaffoldState = rememberScaffoldState()
                val uiState = searchViewModel.uiState.observeAsState().value

                ExoplanetExplorerTheme {
                    Scaffold(
                        topBar = {
                            PlanetSearchBar(
                                query = query,
                                onQueryChanged = searchViewModel::onQueryChanged,
                            )
                        } ,
                        backgroundColor = MaterialTheme.colors.background,
                        content = {
                                MainContent(
                                    uiState = uiState)

                        },
                        drawerContent = {
                            navigationDrawer(searchViewModel)
                        },
                        scaffoldState = scaffoldState,
                        snackbarHost = {scaffoldState.snackbarHostState}
                    )
                }
            }
        }
    }


    @Composable
    fun MainContent(uiState: UiState?){  //Loaded when app is opened
        val listState = rememberLazyListState()
        val cardHeight = 65.dp
        Timber.d("UiState: ${uiState}")
        when(uiState){
            UiState.Empty -> {
                Text("No planets are located", color = MaterialTheme.colors.onPrimary)
            }
            UiState.Loading -> {
                PlanetListLoading(cardHeight = cardHeight)
            }
            UiState.Loaded -> {
                Box(modifier = Modifier.fillMaxSize()) {
                    PlanetListLoaded(listState = listState, cardHeight = cardHeight)
                    Row(modifier = Modifier
                        .wrapContentSize()
                        .align(Alignment.BottomCenter)) {
                        PlanetLoadedSnackBar(snackbarHostState = scaffoldState.snackbarHostState) {}
                    }
                }
            }
            UiState.PlanetsCached -> {
                LaunchedEffect(key1 = "Snackbar", block = {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = "Cached Planets updated",
                        actionLabel = "Hide",
                        duration = SnackbarDuration.Short)
                })
            }
        }
    }

    //Called when data is being retrieved from the network and cached
    @Composable
    fun PlanetListLoading(cardHeight: Dp) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(4.dp),
            contentPadding = PaddingValues(
                horizontal = 16.dp,
                vertical = 4.dp),
        ){
            repeat(20){
                item {
                    ShimmerPlanetCard(cardHeight = cardHeight)
                }
            }
        }
    }

    @Composable
    fun PlanetListLoaded(listState: LazyListState, cardHeight: Dp){
        val scrollState = rememberScrollState()
        LazyColumn(
            modifier = Modifier.scrollable(scrollState, Orientation.Vertical),
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
                        val action = SearchFragmentDirections.viewPlanet(planet.planetName!!)
                        Timber.d("navigating to Planet: ${planet.planetName} with PlanetID: ${planet.planetID}")
                        findNavController().navigate(action)
                    },
                    cardHeight = cardHeight
                )
            }
        }
    }

}




