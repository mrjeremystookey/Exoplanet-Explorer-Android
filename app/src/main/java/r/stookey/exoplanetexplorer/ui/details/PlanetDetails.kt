package r.stookey.exoplanetexplorer.ui.details

import android.util.Log
import android.util.Patterns
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import r.stookey.exoplanetexplorer.domain.Planet


@Composable
fun PlanetDetails(planet: Planet, navigateToReference: (url: String) -> Unit) {
    val textColor = MaterialTheme.colors.onPrimary
    val dividerThickness = 16.dp
    val dividerModifier = Modifier.padding(4.dp)
    LazyColumn(
        Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(MaterialTheme.colors.primary),
    ) {
        item {
            Column {
                Text("Planet Parameters:", color = textColor, fontSize = 24.sp)
                Text("Planet orbit period: ${planet.planetaryOrbitPeriod} days", color = textColor)
                Text("Mass of planet: ${planet.planetaryMassEarth} Earth mass", color = textColor)
                Text("Planet density: ${planet.planetDensity} g/cm**3", color = textColor)
                Text("Planet transit midpoint: ${planet.planetaryTransitMidpoint} days",
                    color = textColor)
                Text("Orbital inclination: ${planet.planetaryOrbitalInclination}",
                    color = textColor)
                Text("Orbital eccentricity: ${planet.planetaryOrbitalEccentricity}",
                    color = textColor)
                Text("Orbit semi-major axis: ${planet.orbitSemiMajorAxis} AU", color = textColor)
            }
            Divider(thickness = dividerThickness, modifier = dividerModifier)
            Column {
                Text("System Composition:", color = textColor, fontSize = 24.sp)
                Text("Number of stars in system: ${planet.systemMoonNumber}", color = textColor)
                Text("Number of planets in system: ${planet.systemPlanetNumber}", color = textColor)
                Text("Number of moons in system: ${planet.systemMoonNumber}", color = textColor)
                Text("Member of a binary system: ${planet.orbitsInBinarySystem}", color = textColor)
            }
            Divider(thickness = dividerThickness, modifier = dividerModifier)
            Column {
                Text("Discovery information:", color = textColor, fontSize = 24.sp)
                Text("Discovery facility: ${planet.discoveryFacility}", color = textColor)
                Text("Discovery method: ${planet.discoveryMethod}", color = textColor)
                Text("Discovery telescope: ${planet.discoveryTelescope}", color = textColor)
                Text("Discovery instrument: ${planet.discoveryInstrument}", color = textColor)
                Text("Discovery year: ${planet.discoveryYear}", color = textColor)
                Text("Discovery pub. date: ${planet.discoveryPublicationDate}", color = textColor)
                Text("Discovery location: ${planet.discoveryLocale}", color = textColor)
            }
            Divider(thickness = dividerThickness, modifier = dividerModifier)
            Column {
                Text("References: ", color = textColor, fontSize = 24.sp)
                AnnotatedClickableText("Orbital period reference",
                    planet.orbitalPeriodReference?.let { extractUrl(it) },
                    planet.orbitalPeriodReference?.let { extractStr(it) }, navigateToReference = navigateToReference)
                AnnotatedClickableText("Planet mass reference",
                    planet.planetaryMassEarthReference?.let { extractUrl(it) },
                    planet.planetaryMassEarthReference?.let { extractStr(it) }, navigateToReference = navigateToReference)
                AnnotatedClickableText("Planet transit midpoint reference",
                    planet.planetaryTransitMidpointReference?.let { extractUrl(it) },
                    planet.planetaryTransitMidpointReference?.let { extractStr(it) }, navigateToReference = navigateToReference)
                AnnotatedClickableText("Discovery reference info",
                    planet.discoveryReference?.let { extractUrl(it) },
                    planet.discoveryReference?.let { extractStr(it) }, navigateToReference = navigateToReference)
                AnnotatedClickableText("Orbit semi-major axis reference",
                    planet.orbitSemiMajorAxisReference?.let { extractUrl(it) },
                    planet.orbitSemiMajorAxisReference?.let { extractStr(it) }, navigateToReference = navigateToReference
                )
            }
        }
    }
}



//Used when linking to outside content in a URL
@Composable
fun AnnotatedClickableText(label: String, url: String?, text: String?, navigateToReference: (url: String) -> Unit) {
    val annotatedText = buildAnnotatedString {
        append("$label: ")
        pushStringAnnotation(
            tag = "URL",
            annotation = url.toString()
        )
        withStyle(style = SpanStyle(color = MaterialTheme.colors.secondary,
            fontWeight = FontWeight.Bold))
        {
            append(text.toString())
        }
        pop()
    }
    ClickableText(
        text = annotatedText,
        onClick = { offset ->
            annotatedText.getStringAnnotations(tag = "URL", start = offset,
                end = offset)
                .firstOrNull()?.let { annotation ->
                    Log.d("Clicked URL", annotation.item)
                    navigateToReference(annotation.item)
                }
        },
        style = TextStyle(
            color = MaterialTheme.colors.onPrimary,
        )
    )
}

//pulls str from html hyperlinks, text to be displayed on screen
private fun extractStr(input: String) =
    input.split(">")[1].split("<")[0]

//pulls url from html hyperlinks, address to be redirected to when a reference is clicked
private fun extractUrl(input: String) =
    input.split(" ").firstOrNull{ Patterns.WEB_URL.matcher(it).find() }?.split("=")?.get(1)


@Preview
@Composable
fun PreviewPlanetDetails(){
    PlanetDetails(Planet(1, "Kepler",
        "Kp", "Kepler", 1,"1334",
        "Eyes", "earth", "test",
        "eyes", "tester", "6",
        "5", "45", "1.3", "2.4"),
        navigateToReference = {})
}


