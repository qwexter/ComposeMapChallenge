package xyz.qwexter.mapscocase.points.mappoints

import com.google.android.gms.maps.model.LatLng
import kotlin.random.Random

class MockMarkerProvider : MarkerProvider {

    override fun getMarkerForNearArea(center: LatLng): List<MapPoint> {
        return (0..2909).map {
            MapPoint(
                LatLng(
                    center.latitude + Random.nextFloat(),
                    center.longitude + Random.nextFloat()
                ),
                "$it"
            )
        }
    }
}