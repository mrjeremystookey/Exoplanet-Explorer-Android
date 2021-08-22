package r.stookey.exoplanetexplorer.domain


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
@Entity(tableName = "planets-db")
data class Planet(
                  @PrimaryKey(autoGenerate = true)
                  @ColumnInfo(name = "planet_id")
                  var planetID: Int? = null,

                  @ColumnInfo(name = "planet_name")
                  @Json(name = "pl_name")
                  val planetName: String? = null,

                  @ColumnInfo(name = "planet_letter")
                  @Json(name = "pl_letter")
                  val planetLetter: String? = null,

                  @ColumnInfo(name = "hostname")
                  @Json(name = "hostname")
                  val hostname: String? = null,

                  @ColumnInfo(name = "discovery_year")
                  @Json(name = "disc_year")
                  val discoveryYear: String? = null,

                  @ColumnInfo(name = "discoverymethod")
                  @Json(name = "disc_method")
                  val discoveryMethod: String? = null,

                  @ColumnInfo(name = "discovery_locale")
                  @Json(name = "disc_locale")
                  val discoveryLocale: String? = null,

                  @ColumnInfo(name = "discovery_facility")
                  @Json(name = "disc_facility")
                  val discoveryFacility: String? = null,

                  @ColumnInfo(name = "discovery_instrument")
                  @Json(name = "disc_instrument")
                  val discoveryInstrument: String? = null,

                  @ColumnInfo(name = "discovery_telescope")
                  @Json(name = "disc_telescope")
                  val discoveryTelescope: String? = null,

                  @ColumnInfo(name = "system_star_number")
                  @Json(name = "sy_snum")
                  val systemStarNumber: String? = null,

                  @ColumnInfo(name = "system_planet_number")
                  @Json(name = "sy_pnum")
                  val systemPlanetNumber: String? = null,
                    //Unit measured in days
                  @ColumnInfo(name = "planetary_orbit_period")
                  @Json(name = "pl_orbper")
                  val planetaryOrbitPeriod: String? = null,

                  @ColumnInfo(name = "planetary_radius_earth")
                  @Json(name="pl_rade")
                  val planetaryRadiusEarth: String? = null,

                  @ColumnInfo(name = "planetary_mass_earth")
                  @Json(name = "pl_bmasse")
                  val planetaryMassEarth: String? = null

){

}

//pl_masse,pl_rade,pl_orbper,sy_pnum,sy_snum,disc_telescope,disc_instrument,disc_facility,disc_locale,disc_method,disc_year,hostname,pl_letter,pl_name