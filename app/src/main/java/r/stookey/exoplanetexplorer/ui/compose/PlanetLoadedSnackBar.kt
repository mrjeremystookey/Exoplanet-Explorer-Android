package r.stookey.exoplanetexplorer.ui.compose

import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.dp

@Composable
fun PlanetLoadedSnackBar(
    snackbarHostState: SnackbarHostState,
    modifier: Modifier = Modifier,
    onDismiss:() -> Unit,
) {
    SnackbarHost(
        modifier = Modifier.alpha(.9f),
        hostState = snackbarHostState,
        snackbar = { data ->
            Snackbar(
                backgroundColor = MaterialTheme.colors.secondary,
                modifier = Modifier.padding(16.dp),
                content = {
                    Text(
                        text = data.message,
                        style = MaterialTheme.typography.body2,
                        color = MaterialTheme.colors.onSecondary
                    )
                },
                action = {
                    data.actionLabel?.let { actionLabel ->
                        TextButton(
                            onClick = {onDismiss()}
                        ){
                            Text(
                                text = actionLabel,
                                style = MaterialTheme.typography.body2,
                                color = MaterialTheme.colors.onSecondary
                            )
                        }
                    }
                }
            )
        }
    )
}

