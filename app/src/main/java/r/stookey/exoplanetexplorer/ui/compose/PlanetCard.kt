package r.stookey.exoplanetexplorer.ui.compose

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import r.stookey.exoplanetexplorer.domain.Planet


@Composable
fun PlanetCard(
    planet: Planet,
    navigateToPlanet: () -> Unit,
    cardHeight: Dp
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
            .height(cardHeight)
            .clickable(onClick = navigateToPlanet),
        elevation = 8.dp
    ){
        Column(modifier = Modifier.padding(4.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {
            Text("Planet Name: " + planet.planetName)
            Divider(thickness = 4.dp)
            Text("Hostname: " + planet.hostname)
        }
    }
}

@Preview
@Composable
fun PreviewPlanetCard(){
    PlanetCard(
        planet = Planet(1, "And Cheeks", "Kepler", "Kepler"),
        cardHeight = 75.dp,
        navigateToPlanet = {}
    )
}







