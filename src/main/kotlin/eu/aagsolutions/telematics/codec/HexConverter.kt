/*
 * Copyright (c) 2024 Aurel Avramescu.
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the “Software”), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */

package eu.aagsolutions.telematics.codec

/**
 * Convert a hexadecimal string to a byte array.
 * @param hexString hexadecimal string
 * @return byte array representation
 */
fun hexStringToByteArray(hexString: String): ByteArray {
    val len = hexString.length
    val data = ByteArray(len / 2)
    for (i in 0 until len step 2) {
        data[i / 2] = ((Character.digit(hexString[i], 16) shl 4)
                + Character.digit(hexString[i + 1], 16)).toByte()
    }
    return data
}

/**
 * Convert byte array to a hexadecimal string.
 * @param bytes
 * @return string representation
 */
fun bytesToHex(bytes: ByteArray): String {
    val hexString = StringBuilder()
    for (aByte in bytes) {
        val hex = (aByte.toInt() and 0xFF).toString(16)
        if (hex.length == 1) {
            hexString.append('0')
        }
        hexString.append(hex)
    }
    return hexString.toString()
}

/**
 * Transform a hexadecimal string to a binary string.
 * @param hex hexadecimal representation
 * @return binary representation
 */
fun hexToBinary(hex: String): String {
    return hex.map {
        val binary = Integer.toBinaryString(it.toString().toInt(16))
        String.format("%4s", binary).replace(' ', '0')
    }.joinToString("")
}

/**
 * Encode latitude/longitude to geohash.
 * @param latitude latitude value
 * @param longitude longitude value
 * @param precision geohash precision
 * @return geohash representation
 */
fun encodeGeoHash(latitude: Double, longitude: Double, precision: Int = 12): String {
    val base32Map = "0123456789bcdefghjkmnpqrstuvwxyz"
    var minLat = -90.0
    var maxLat = 90.0
    var minLon = -180.0
    var maxLon = 180.0

    val geohash = StringBuilder()
    var isEvenBit = true
    var bits = 0
    var base32CharIndex = 0

    while (geohash.length < precision) {
        if (isEvenBit) {
            // Process longitude
            val mid = (minLon + maxLon) / 2.0
            if (longitude >= mid) {
                base32CharIndex = (base32CharIndex shl 1) or 1
                minLon = mid
            } else {
                base32CharIndex = base32CharIndex shl 1
                maxLon = mid
            }
        } else {
            // Process latitude
            val mid = (minLat + maxLat) / 2.0
            if (latitude >= mid) {
                base32CharIndex = (base32CharIndex shl 1) or 1
                minLat = mid
            } else {
                base32CharIndex = base32CharIndex shl 1
                maxLat = mid
            }
        }
        isEvenBit = !isEvenBit
        bits++

        if (bits == 5) {
            // Convert the bits to a character
            geohash.append(base32Map[base32CharIndex])
            // Reset bits
            bits = 0
            base32CharIndex = 0
        }
    }
    return geohash.toString()
}

