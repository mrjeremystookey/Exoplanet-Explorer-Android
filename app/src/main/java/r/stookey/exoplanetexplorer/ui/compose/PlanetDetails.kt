package r.stookey.exoplanetexplorer.ui.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import r.stookey.exoplanetexplorer.domain.Planet



@Composable
fun PlanetDetails(planet: Planet){
    Column(
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.primary),
    ) {
        Text("Planet Name: ${planet.planetName}")
        Text("Number of planets in System: ${planet.systemPlanetNumber}")
        Text("Mass of planet, earth mass: ${planet.planetaryMassEarth}")
        Text("Discovery Facility: ${planet.discoveryFacility}")
        Text("Discovery Method: ${planet.discoveryMethod}")
        Text("Discovery Telescope: ${planet.discoveryTelescope}")
        Text("Discovery Year: ${planet.discoveryYear}")
        Text("Planetary Orbital Period, days: ${planet.planetaryOrbitPeriod}")
    }
}


@Preview
@Composable
fun PreviewPlanetDetails(){
    PlanetDetails(Planet(1, "Kepler",
        "Kp", "Kepler", "1566",
        "Eyes", "earth", "test",
        "eyes", "tester", "6",
        "5", "45", "1.3", "2.4"))
}