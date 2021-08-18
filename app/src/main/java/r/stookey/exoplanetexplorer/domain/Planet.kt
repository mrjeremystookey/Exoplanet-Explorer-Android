package r.stookey.exoplanetexplorer.domain


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class Planet(var pl_id: String? = null,
                  @Json(name = "pl_name")
                  val pl_name: String? = null,
                  @Json(name = "pl_letter")
                  val pl_letter: String? = null,
                  @Json(name = "hostname")
                  val hostname: String? = null,
                  @Json(name = "disc_pub_date")
                  val disc_pub_date: String? = null,
                  @Json(name = "disc_year")
                  val disc_year: String? = null,
                  @Json(name = "disc_method")
                  val disc_method: String? = null,
                  @Json(name = "disc_locale")
                  val disc_locale: String? = null,
                  @Json(name = "disc_facility")
                  val dic_facility: String? = null,
                  @Json(name = "disc_instrument")
                  val disc_instrument: String? = null,
                  @Json(name = "disc_telescope")
                  val disc_telescope: String? = null,
){

}
