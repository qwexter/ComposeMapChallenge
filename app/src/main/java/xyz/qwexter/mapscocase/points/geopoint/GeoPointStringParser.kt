package xyz.qwexter.mapscocase.points.geopoint

import android.util.Log

internal interface GeoPointStringParser {
    fun parse(input: String?): GeoPoint?
}

internal class GeoPointStringParserImpl : GeoPointStringParser {

    private companion object {
        val TAG = this::class.qualifiedName
    }

    override fun parse(input: String?): GeoPoint? {
        if (input.isNullOrBlank()) return null

        val values = input.split(",").map { it.trim() }
        return try {
            GeoPoint(
                id = values[1].toLong(),
                lat = values[2].toDouble(),
                lng = values[3].toDouble()
            )
        } catch (exception: Throwable) {
            Log.d(TAG, "Can't parse $input", exception)
            null
        }
    }

}
