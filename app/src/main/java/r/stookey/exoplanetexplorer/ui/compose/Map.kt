package r.stookey.exoplanetexplorer.ui.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.coroutines.launch



val SYDNEY = LatLng(-33.862, 151.21)





    @Composable
     fun CityMapView(latitude: String, longitude: String) {
        // The MapView lifecycle is handled by this composable. As the MapView also needs to be updated
        // with input from Compose UI, those updates are encapsulated into the MapViewContainer
        // composable. In this way, when an update to the MapView happens, this composable won't
        // recompose and the MapView won't need to be recreated.
        val mapView = rememberMapViewWithLifecycle()
        MapViewContainer(mapView, latitude, longitude)
    }

    @Composable
    private fun MapViewContainer(
        map: MapView,
        latitude: String,
        longitude: String
    ) {

        val cameraPosition = remember(latitude, longitude) {
            LatLng(latitude.toDouble(), longitude.toDouble())
        }

        LaunchedEffect(map) {
            val googleMap = map.getMapAsync { googleMap ->
                googleMap.moveCamera(CameraUpdateFactory.newLatLng(cameraPosition))
                googleMap.addMarker(MarkerOptions().position(cameraPosition))
            }
        }

        val coroutineScope = rememberCoroutineScope()
        AndroidView({ map }) { mapView ->
            // Reading zoom so that AndroidView recomposes when it changes. The getMapAsync lambda
            // is stored for later, Compose doesn't recognize state reads
            coroutineScope.launch {
                val googleMap = mapView.getMapAsync {googleMap ->
                    // Move camera to the same place to trigger the zoom update
                    googleMap.moveCamera(CameraUpdateFactory.newLatLng(cameraPosition))
                }

            }
        }
    }




const val MinZoom = 2f
const val MaxZoom = 20f