package xyz.qwexter.mapscocase.points.mappoints

import com.google.android.gms.maps.model.LatLng
import xyz.qwexter.mapscocase.points.geopoint.GeoPointRepository

class FileMarkerProvider(private val geoPointRepository: GeoPointRepository) : MarkerProvider {

    override suspend fun getMarkerForNearArea(cameraPosition: LatLng): List<MapPoint> {
        return geoPointRepository.read(cameraPosition.latitude, cameraPosition.longitude)
            .map { geoPoint ->
                MapPoint(
                    LatLng(geoPoint.lat, geoPoint.lng), geoPoint.id.toString()
                )
            }
    }
}