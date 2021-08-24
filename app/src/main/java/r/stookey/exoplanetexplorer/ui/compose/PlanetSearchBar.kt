package r.stookey.exoplanetexplorer.ui.compose

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import r.stookey.exoplanetexplorer.ui.dashboard.DashboardViewModel


@ExperimentalComposeUiApi
@Composable
fun PlanetSearchBar(query: MutableState<String>, dashboardViewModel: DashboardViewModel){
    val keyboardController = LocalSoftwareKeyboardController.current
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = MaterialTheme.colors.primary,
        elevation = 8.dp
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),

        ){
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                value = query.value,
                onValueChange = { newQuery ->
                    dashboardViewModel.onQueryChanged(newQuery)
                },
                label = {
                    Text("Search", color = MaterialTheme.colors.onPrimary)
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Search
                ),
                keyboardActions = KeyboardActions(onSearch = {
                    dashboardViewModel.newSearchByPlanetName(query.value)
                    keyboardController?.hide()
                })
                ,
                leadingIcon = {
                    Icon(Icons.Outlined.Search, "search")
                },
                textStyle = TextStyle(color = MaterialTheme.colors.onPrimary)
            )
        }
    }
}
