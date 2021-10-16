package r.stookey.exoplanetexplorer.ui.compose

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import r.stookey.exoplanetexplorer.viewmodels.SearchViewModel


@Composable
fun navigationDrawer(searchViewModel: SearchViewModel){




    Column(Modifier.wrapContentSize()) {
        Button(
            modifier = Modifier.padding(4.dp),
            onClick = searchViewModel::networkButtonPressed
        ) {
            Text("Check for new planets")
        }
        Button(
            modifier = Modifier.padding(4.dp),
            onClick = searchViewModel::clearCacheButtonPressed,
        ) {
            Text("Clear Cache")
        }
        Row(){
            Text("Number of planets cached: ${searchViewModel.planetsList.value.size}")
        }
    }
}