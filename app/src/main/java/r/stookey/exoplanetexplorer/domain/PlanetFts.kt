package r.stookey.exoplanetexplorer.domain

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Fts4


@Entity(tableName = "planets_fts")
@Fts4(contentEntity = Planet::class)
data class PlanetFts(@ColumnInfo(name = "planet_name")
                     val planetName: String,
                     @ColumnInfo(name = "planet_id")
                     val planetId: String,
                     @ColumnInfo(name = "planet_letter")
                     val planetLetter: String? = null,
                     @ColumnInfo(name = "hostname")
                     val hostname: String? = null,
                     @ColumnInfo(name = "discovery_year")
                     val discoveryYear: String? = null,
                     @ColumnInfo(name = "discovery_method")
                     val discoveryMethod: String? = null,
                     @ColumnInfo(name = "discovery_locale")
                     val discoveryLocale: String? = null,
                     @ColumnInfo(name = "discovery_facility")
                     val discoveryFacility: String? = null,
                     @ColumnInfo(name = "discovery_instrument")
                     val discoveryInstrument: String? = null,
                     @ColumnInfo(name = "discovery_telescope")
                     val discoveryTelescope: String? = null,
                     @ColumnInfo(name = "system_star_number")
                     val systemStarNumber: String? = null,
                     @ColumnInfo(name = "system_planet_number")
                     val systemPlanetNumber: String? = null,
                     @ColumnInfo(name = "planetary_orbit_period")
                     val planetaryOrbitPeriod: String? = null,
                     @ColumnInfo(name = "planetary_radius_earth")
                     val planetaryRadiusEarth: String? = null,
                     @ColumnInfo(name = "planetary_mass_earth")
                     val planetaryMassEarth: String? = null
)