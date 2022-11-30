package xyz.qwexter.mapscocase.points.mappoints

import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterItem

data class MapPoint(
    private val position: LatLng,
    private val title: String,
) : ClusterItem {
    override fun getPosition(): LatLng = position

    override fun getTitle(): String = title

    override fun getSnippet(): String? = null
}