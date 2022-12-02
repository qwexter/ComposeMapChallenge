package xyz.qwexter.mapscocase.points.geopoint

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import xyz.qwexter.mapscocase.R

internal class GeoPointRepositoryImpl(private val context: Context) : GeoPointRepository {

    override suspend fun read(lat: Double, lng: Double): List<GeoPoint> {
        val reader = context.resources.openRawResource(R.raw.hotspots).bufferedReader()
        return withContext(Dispatchers.IO) {
            val parser: GeoPointStringParser = GeoPointStringParserImpl()
            reader.readLines().mapNotNull(parser::parse)
        }
    }

}