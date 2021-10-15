package r.stookey.exoplanetexplorer.domain

sealed class SortStatus(var units: String) {
    object EarthMass: SortStatus("Mass, Earth")
    object EarthRadius: SortStatus("Radius, Earth")
    object Period: SortStatus("Period, Days")
    object Density: SortStatus("Density, g/cm**3")
    object Insolation: SortStatus("Insolation, Earth Flux")
    object JupiterMass: SortStatus("Mass, Jupiter")
    object JupiterRadius: SortStatus("Radius, Jupiter")
    object SemiMajorAxis: SortStatus("Semi-Major Axis, AU")
    object OrbitalEccentricity: SortStatus("Eccentricity")
    object EquilibriumTemperature: SortStatus("Equilibrium Temperature, K")
}