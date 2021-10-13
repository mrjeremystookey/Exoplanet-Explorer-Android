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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
        Row(Modifier.fillMaxWidth()){
            Column(
                Modifier
                    .fillMaxWidth(.9f)
                    .padding(start = 8.dp)
                    .align(Alignment.CenterVertically),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start) {
                Row(Modifier){
                    Text("Name: " + planet.planetName, fontSize = 16.sp)
                }
                Row(Modifier.padding(top = 4.dp)){
                    Text("Host: " + planet.hostname, fontSize = 12.sp)
                }
            }
            Column(
                Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(.8f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.End) {
                Text(modifier = Modifier,
                    text = "${planet.discoveryYear}",
                    fontSize = 12.sp,
                )
            }

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







