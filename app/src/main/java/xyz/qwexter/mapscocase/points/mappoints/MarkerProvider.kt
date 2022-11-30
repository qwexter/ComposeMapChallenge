package xyz.qwexter.mapscocase.points.mappoints

import com.google.android.gms.maps.model.LatLng

interface MarkerProvider {
    fun getMarkerForNearArea(center: LatLng): List<MapPoint>
}