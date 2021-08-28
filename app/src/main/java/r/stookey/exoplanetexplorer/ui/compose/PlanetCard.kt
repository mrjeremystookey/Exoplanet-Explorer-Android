package r.stookey.exoplanetexplorer.ui.compose

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import r.stookey.exoplanetexplorer.domain.Planet


@Composable
fun PlanetCard(
    planet: Planet,
    navigateToPlanet: () -> Unit,
){
    Card(
        backgroundColor = MaterialTheme.colors.primary,
        contentColor = MaterialTheme.colors.onPrimary,
        shape = MaterialTheme.shapes.medium,
        modifier = Modifier
            .padding(
                bottom = 6.dp,
                top = 6.dp,
            )
            .fillMaxWidth()
            .clickable(onClick = navigateToPlanet),
        elevation = 8.dp
    ){
        Column {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End)
                {
                Text("Planet Name: " + planet.planetName)
                Spacer(modifier = Modifier.padding(horizontal = 32.dp))
                Text("Hostname: " + planet.hostname)
                }
            Text("PlanetID: " + planet.planetID)
            Text("Planet Letter: ${planet.planetLetter}")

        }
    }
}

@Preview
@Composable
fun PreviewPlanetCard(){
    PlanetCard(Planet(1, "And Cheeks", "Kepler", "Kepler")) {}
}







