package eu.aagsolutions.telematics.codec

import kotlin.test.Test
import kotlin.test.assertEquals

class HexConverterKtTest {

    @Test
    fun `it should encode gps coordinates to GeoHash successfully`() {
        val latitude = 57.64911
        val longitude = 10.40744
        val geoHash = encodeGeoHash(latitude, longitude)
        assertEquals("u4pruydqqvj8", geoHash)
    }
}