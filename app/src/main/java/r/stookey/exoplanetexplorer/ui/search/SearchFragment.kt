package r.stookey.exoplanetexplorer.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import r.stookey.exoplanetexplorer.R
import r.stookey.exoplanetexplorer.databinding.FragmentSearchBinding
import r.stookey.exoplanetexplorer.ui.compose.*
import r.stookey.exoplanetexplorer.ui.compose.theme.ExoplanetExplorerTheme
import timber.log.Timber

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private val searchViewModel: SearchViewModel by activityViewModels()
    private var binding: FragmentSearchBinding? = null
    private lateinit var scaffoldState: ScaffoldState
    private lateinit var query: State<String>
    private val expanded = mutableStateOf(false)


    override fun onDestroyView() {
        super.onDestroyView()
        Timber.i("onDestroyView called")
        binding = null
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
                        topBar = { SearchAndSortBar() },
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

    //Contains search bar and sort options for list of planets
    @ExperimentalComposeUiApi
    @Composable
    fun SearchAndSortBar(){
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colors.primary,
            elevation = 8.dp
        ) {
            Row(
                modifier = Modifier
                    .height(72.dp),
            ){
                PlanetSearchBar(
                    modifier = Modifier
                        .weight(.8f),
                    query = query,
                    onQueryChanged = searchViewModel::onQueryChanged,
                )
                Box(modifier = Modifier.weight(.2f)){
                    Button(
                        modifier = Modifier
                            .padding(end = 8.dp, bottom = 6.dp, top = 6.dp)
                            .fillMaxSize()
                            .background(MaterialTheme.colors.primaryVariant),
                        border = BorderStroke(2.dp, MaterialTheme.colors.secondary),
                        onClick = { expanded.value = true
                            Timber.d("expanded value changed")
                        })
                    {
                        Text(text = "Sort By",
                            color = MaterialTheme.colors.secondary,
                            fontSize = 14.sp)
                        IconButton(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(start = 4.dp),
                            onClick = { searchViewModel.changeAscendingDescending() },
                            content = {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_baseline_sort_24),
                                    contentDescription = "Ascending/Descending Order",
                                    tint = MaterialTheme.colors.secondaryVariant
                                )
                            })
                    }
                    DropdownMenu(
                        modifier = Modifier
                            .wrapContentSize()
                            .background(MaterialTheme.colors.primaryVariant),
                        content = { SortMenu() },
                        offset = DpOffset(10.dp, 8.dp),
                        expanded = expanded.value,
                        onDismissRequest = { expanded.value = false },
                    )
                }
            }
        }
    }

    //Shown when Sort button in Sort menu is clicked
    @Composable
    fun SortMenu(){
            Column(Modifier.fillMaxSize()) {
                SortItem(sort = SortStatus.EarthMass)
                SortItem(sort = SortStatus.EarthRadius)
                SortItem(sort = SortStatus.Period)
                SortItem(sort = SortStatus.Density)
            }
        }

    //Items shown when sort menu is clicked
    @Composable
    fun SortItem(sort: SortStatus){
        val dividerModifier = Modifier.padding(vertical = 4.dp)
        val rowModifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.primary)
            .padding(4.dp)
        val textModifier = Modifier.padding(8.dp)
        Row(rowModifier.clickable {
            Timber.d("Sort by ${sort.units}")
            searchViewModel.onSortClicked(sort)
            expanded.value = false
        }) {
            Text(modifier = textModifier, text = sort.units)
        }
        Divider(dividerModifier, color = MaterialTheme.colors.secondaryVariant, thickness = 1.dp)
    }

    //Main window between search bar and bottom bar
    @Composable
    fun MainContent(uiState: UiState?){  //Loaded when app is opened
        val listState = rememberLazyListState()
        val cacheState = searchViewModel.cacheState.observeAsState().value
        val cardHeight = 75.dp
        Timber.d("UiState: $uiState")
        when(uiState){
            UiState.Empty -> {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("fetching planets...", color = MaterialTheme.colors.onPrimary)
                    CircularIndeterminateProgressBar()
                }
            }
            UiState.Loading -> {
                PlanetListLoading(cardHeight = cardHeight)
            }
            UiState.Loaded -> {
                Box(modifier = Modifier.fillMaxSize()) {
                    if(cacheState == true){
                        LaunchedEffect(key1 = "Snackbar", block = {
                            scaffoldState.snackbarHostState.showSnackbar(
                                message = "Cached Planets updated",
                                actionLabel = "Hide",
                                duration = SnackbarDuration.Short)
                        })
                    }
                    PlanetListLoaded(listState = listState, cardHeight = cardHeight)
                    Row(modifier = Modifier
                        .wrapContentSize()
                        .align(Alignment.BottomCenter)) {
                        PlanetLoadedSnackBar(snackbarHostState = scaffoldState.snackbarHostState) {}
                    }
                }
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

    //Called when planet data is cached and being displayed
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




