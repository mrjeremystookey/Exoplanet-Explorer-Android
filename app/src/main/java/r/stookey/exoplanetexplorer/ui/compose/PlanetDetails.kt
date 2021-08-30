package r.stookey.exoplanetexplorer.ui.compose

import android.util.Log
import android.util.Patterns
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
fun PlanetDetails(planet: Planet){
    val color = MaterialTheme.colors.onPrimary
    Column(
        Modifier
            .fillMaxSize()
            .padding(4.dp)
            .background(MaterialTheme.colors.primary),
    ) {
        Row{
            Text("Planet Name: ${planet.planetName}", color = MaterialTheme.colors.onPrimary, fontSize = 32.sp)
        }
        Divider(thickness = 16.dp)
        Text("Planetary Parameters:", color = MaterialTheme.colors.onPrimary, fontSize = 24.sp)
        val orbPerStr = planet.orbitalPeriodReference?.let{ extractStr(it)}
        val orbPerUrl = planet.orbitalPeriodReference?.let { extractUrl(it) }
        Text("Planetary orbital period, days: ${planet.planetaryOrbitPeriod}", color = MaterialTheme.colors.onPrimary)
        AnnotatedClickableText(orbPerUrl, orbPerStr, "Orbital period reference")
        Text("Mass of planet, Earth mass: ${planet.planetaryMassEarth}", color = MaterialTheme.colors.onPrimary)
        Text("Orbital inclination: ${planet.planetaryOrbitalInclination}", color = MaterialTheme.colors.onPrimary)
        Text("Orbital eccentricity: ${planet.planetaryOrbitalEccentricity}", color = MaterialTheme.colors.onPrimary)
        Divider(thickness = 16.dp)
        Text("System Composition:", color = MaterialTheme.colors.onPrimary, fontSize = 24.sp)
        Text("Number of stars in system: ${planet.systemMoonNumber}", color = MaterialTheme.colors.onPrimary)
        Text("Number of planets in system: ${planet.systemPlanetNumber}", color = MaterialTheme.colors.onPrimary)
        Text("Number of moons in system: ${planet.systemMoonNumber}", color = MaterialTheme.colors.onPrimary)
        Text("Member of a binary system: ${planet.orbitsInBinarySystem}", color = MaterialTheme.colors.onPrimary)

        Divider(thickness = 16.dp)
        Column {
            Text("Discovery information:", color = MaterialTheme.colors.onPrimary, fontSize = 24.sp)
            Text("Discovery facility: ${planet.discoveryFacility}", color = MaterialTheme.colors.onPrimary)
            Text("Discovery method: ${planet.discoveryMethod}", color = MaterialTheme.colors.onPrimary)
            Text("Discovery telescope: ${planet.discoveryTelescope}", color = MaterialTheme.colors.onPrimary)
            Text("Discovery year: ${planet.discoveryYear}", color = MaterialTheme.colors.onPrimary)
            val discRefStr = planet.discoveryReferenceName?.let { extractStr(it) }
            val discRefUrl = planet.discoveryReferenceName?.let { extractUrl(it) }

            AnnotatedClickableText(discRefUrl, discRefStr, "Discovery Reference Info")
        }

    }
}


@Composable
fun AnnotatedClickableText(url: String?, text: String?, label: String) {
    val annotatedText = buildAnnotatedString {
        append("$label: ")
        pushStringAnnotation(
            tag = "URL",
            annotation = url.toString()
        )
        withStyle(style = SpanStyle(color = Color.Yellow,
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
                    //Navigate to webview with URL
                }
        },
        style = TextStyle(
            color = MaterialTheme.colors.onPrimary,
        )
    )
}

private fun extractStr(input: String) =
    input.split(">")[1].split("<")[0]

private fun extractUrl(input: String) =
    input.split(" ").firstOrNull{ Patterns.WEB_URL.matcher(it).find() }?.split("=")?.get(1)


@Preview
@Composable
fun PreviewPlanetDetails(){
    PlanetDetails(Planet(1, "Kepler",
        "Kp", "Kepler", 1,"1334",
        "Eyes", "earth", "test",
        "eyes", "tester", "6",
        "5", "45", "1.3", "2.4"))
}


