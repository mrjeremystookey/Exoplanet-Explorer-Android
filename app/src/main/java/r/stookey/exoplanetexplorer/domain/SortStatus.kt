package r.stookey.exoplanetexplorer.domain


//Could this be moved to an enum class to reduce overhead?
sealed class SortStatus(var units: String) {
    object EarthMass: SortStatus("Mass, Earth")
    object EarthRadius: SortStatus("Radius, Earth")
    object JupiterMass: SortStatus("Mass, Jupiter")
    object JupiterRadius: SortStatus("Radius, Jupiter")
    object Insolation: SortStatus("Insolation, Earth Flux")
    object OrbitalEccentricity: SortStatus("Eccentricity")
    object EquilibriumTemperature: SortStatus("Equilibrium Temperature, K")
    object SemiMajorAxis: SortStatus("Semi-Major Axis, AU")
    object Period: SortStatus("Period, Days")
    object Density: SortStatus("Density, g/cm**3")
}


