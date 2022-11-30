package xyz.qwexter.mapscocase.clusters.markers

import com.google.android.gms.maps.model.LatLng
import kotlin.random.Random

class MockMarkerProvider : MarkerProvider {

    override fun getMarkerForNearArea(center: LatLng): List<MapMarker> {
        return (0..2909).map {
            MapMarker(
                LatLng(
                    center.latitude + Random.nextFloat(),
                    center.longitude + Random.nextFloat()
                ),
                "$it"
            )
        }
    }
}