package xyz.qwexter.mapscocase.points.mappoints

import com.google.android.gms.maps.model.LatLng

interface MarkerProvider {
    suspend fun getMarkerForNearArea(cameraPosition: LatLng): List<MapPoint>
}