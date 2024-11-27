package eu.aagsolutions.telematics.codec

import eu.aagsolutions.telematics.model.Encoded
import kotlin.test.Test
import kotlin.test.assertEquals

class Codec12EncoderTest {
    @Test
    fun `it should successful encode getinfo cmd`() {
        val codec = Codec12Encoder("getinfo", "defaultImei")
        val encoded = codec.encode()
        val expected = Encoded("defaultImei", "000000000000000F0C010500000007676574696E666F0100004312")
        assertEquals(expected, encoded)
    }

    @Test
    fun `it should successful encode getvin cmd`() {
        val codec = Codec12Encoder("getvin", "defaultImei")
        val encoded = codec.encode()
        val expected = Encoded("defaultImei", "000000000000000e0c01050000000667657476696e010000670a".uppercase())
        assertEquals(expected, encoded)
    }
}
