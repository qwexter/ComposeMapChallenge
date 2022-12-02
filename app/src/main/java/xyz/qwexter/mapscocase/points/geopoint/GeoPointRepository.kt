package xyz.qwexter.mapscocase.points.geopoint

import android.content.Context

interface GeoPointRepository {

    companion object {
        fun create(context: Context): GeoPointRepository = GeoPointRepositoryImpl(context)
    }

    suspend fun read(lat: Double, lng: Double): List<GeoPoint>

}

data class GeoPoint(
    val id: Long,
    val lat: Double,
    val lng: Double
)