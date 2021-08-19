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
                  var pl_id: Int? = null,

                  @ColumnInfo(name = "planet_name")
                  @Json(name = "pl_name")
                  val pl_name: String? = null,

                  @ColumnInfo(name = "planet_letter")
                  @Json(name = "pl_letter")
                  val pl_letter: String? = null,

                  @ColumnInfo(name = "hostname")
                  @Json(name = "hostname")
                  val hostname: String? = null,

                  @ColumnInfo(name = "discovery_published_date")
                  @Json(name = "disc_pub_date")
                  val disc_pub_date: String? = null,

                  @ColumnInfo(name = "discovery_year")
                  @Json(name = "disc_year")
                  val disc_year: String? = null,

                  @ColumnInfo(name = "discovery_method")
                  @Json(name = "disc_method")
                  val disc_method: String? = null,

                  @ColumnInfo(name = "discovery_locale")
                  @Json(name = "disc_locale")
                  val disc_locale: String? = null,

                  @ColumnInfo(name = "discovery_facility")
                  @Json(name = "disc_facility")
                  val dic_facility: String? = null,

                  @ColumnInfo(name = "discovery_instrument")
                  @Json(name = "disc_instrument")
                  val disc_instrument: String? = null,

                  @ColumnInfo(name = "discovery_telescope")
                  @Json(name = "disc_telescope")
                  val disc_telescope: String? = null,
){

}

