package r.stookey.exoplanetexplorer.ui.compose

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@ExperimentalComposeUiApi
@Composable
fun PlanetSearchBar(query: State<String>,
                    onQueryChanged: (String) -> Unit,
                    onPlanetSearched: (String) -> Unit,
){
    val keyboardController = LocalSoftwareKeyboardController.current
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = MaterialTheme.colors.primary,
        elevation = 8.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(72.dp),

        ){
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                value = query.value,
                onValueChange = { newQuery ->
                    onQueryChanged(newQuery)
                },
                label = {
                    Text("Search", color = MaterialTheme.colors.secondary, fontSize = 18.sp)
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Search
                ),
                keyboardActions = KeyboardActions(onSearch = {
                    onPlanetSearched(query.value)
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
