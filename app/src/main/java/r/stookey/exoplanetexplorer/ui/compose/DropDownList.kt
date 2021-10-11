package r.stookey.exoplanetexplorer.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import timber.log.Timber

@Composable
fun DropDownList(
    requestToOpen: Boolean = false,
    list: List<String>, request: (Boolean) -> Unit,
    selectedString: (String) -> Unit) {
    DropdownMenu(
        expanded = requestToOpen,
        onDismissRequest = { request(false) },
    ) {
        list.forEach {
            DropdownMenuItem(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    request(false)
                    selectedString(it)
                    Timber.d("$it")
                }
            ) {
                Text(it, modifier = Modifier.wrapContentWidth())
            }
        }
    }
}