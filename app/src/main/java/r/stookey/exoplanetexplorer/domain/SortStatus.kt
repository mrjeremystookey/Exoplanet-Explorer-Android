package r.stookey.exoplanetexplorer.domain

import r.stookey.exoplanetexplorer.R


//Could this be moved to an enum class to reduce overhead?
sealed class SortStatus(var units: String, var label: Int?) {
    object EarthMass: SortStatus("Mass, Earth", R.string.earth_mass_label)
    object EarthRadius: SortStatus("Radius, Earth", R.string.jupiter_radius_label)
    object JupiterMass: SortStatus("Mass, Jupiter", R.string.jupiter_mass_label)
    object JupiterRadius: SortStatus("Radius, Jupiter", R.string.jupiter_radius_label)
    object Insolation: SortStatus("Insolation, Earth Flux", R.string.insolation_earth_label)
    object OrbitalEccentricity: SortStatus("Eccentricity", R.string.eccentricity_label)
    object EquilibriumTemperature: SortStatus("Equilibrium Temperature, K", R.string.equilibrium_temperature_label)
    object SemiMajorAxis: SortStatus("Semi-Major Axis, AU", R.string.au_label)
    object Period: SortStatus("Period, Days", R.string.period_label)
    object Density: SortStatus("Density, g/cm**3", R.string.density_label)
}



