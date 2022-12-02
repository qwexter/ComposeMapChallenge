package xyz.qwexter.mapscocase.points.geopoint

import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import xyz.qwexter.mapscocase.points.geopoint.GeoPoint
import xyz.qwexter.mapscocase.points.geopoint.GeoPointStringParserImpl

@RunWith(Parameterized::class)
class GeoPointStringParserImplTest(private val input: String?, private val expected: GeoPoint?) {

    companion object {
        @JvmStatic
        @Parameterized.Parameters
        fun data(): Collection<Array<Any?>> = listOf(
            arrayOf(null, null),
            arrayOf("", null),
            arrayOf("null", null),
            arrayOf("null, null, null, null", null),
            arrayOf("null, 1, null, null", null),
            arrayOf("null, 1, 0.01, null", null),
            arrayOf("null, 1, 0.0, 0.0", GeoPoint(1, .0, .0)),
        )
    }

    @Test
    fun `expect right parsed geo point`() {
        val actual = GeoPointStringParserImpl().parse(input)
        assertEquals("For $input expected $expected, but got $actual", expected, actual)
    }

}
