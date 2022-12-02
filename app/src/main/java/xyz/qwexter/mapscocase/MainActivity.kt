package xyz.qwexter.mapscocase

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import com.google.android.gms.maps.model.LatLng
import xyz.qwexter.mapscocase.commonui.MapsCoCaseTheme
import xyz.qwexter.mapscocase.points.ClusteredMap
import xyz.qwexter.mapscocase.points.geopoint.GeoPointRepository
import xyz.qwexter.mapscocase.points.mappoints.FileMarkerProvider

class MainActivity : ComponentActivity() {

    private companion object {
        val StartPoint = LatLng(30.0, 30.0)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MapsCoCaseTheme {
                Column {
                    Text(
                        text = "MapsCoCase project shows how to use Google Maps with Compose " +
                                "and work with big count of local data"
                    )
                    ClusteredMap(
                        StartPoint,
                        FileMarkerProvider(GeoPointRepository.create(applicationContext))
                    )
                }
            }
        }
    }
}