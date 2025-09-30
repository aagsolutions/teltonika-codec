/*
 * Copyright (c) 2024 Aurel Avramescu.
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the “Software”), to deal
 * in the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 * the Software, and to permit persons to whom the Software is furnished to do
 * so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 * OTHER DEALINGS IN THE SOFTWARE.
 *
 */

package eu.aagsolutions.telematics.codec

import eu.aagsolutions.telematics.model.Encoded
import kotlin.test.Test
import kotlin.test.assertEquals

class Codec12EncoderTest {
    @Test
    fun `it should successful encode getinfo cmd`() {
        val codec = Codec12Encoder()
        val encoded = codec.encode("getinfo", "defaultImei")
        val expected = Encoded("defaultImei", "000000000000000F0C010500000007676574696E666F0100004312")
        assertEquals(expected, encoded)
    }

    @Test
    fun `it should successful encode getvin cmd`() {
        val codec = Codec12Encoder()
        val encoded = codec.encode("getvin", "defaultImei")
        val expected = Encoded("defaultImei", "000000000000000e0c01050000000667657476696e010000670a".uppercase())
        assertEquals(expected, encoded)
    }
}
