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
        data[i / 2] =
            (
                (Character.digit(hexString[i], 16) shl 4) +
                    Character.digit(hexString[i + 1], 16)
            ).toByte()
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
fun encodeGeoHash(
    latitude: Double,
    longitude: Double,
    precision: Int = 12,
): String {
    val base32Map = "0123456789bcdefghjkmnpqrstuvwxyz"
    var minLat = -90.0
    var maxLat = 90.0
    var minLon = -180.0
    var maxLon = 180.0

    val geoHash = StringBuilder()
    var isEvenBit = true
    var bits = 0
    var base32CharIndex = 0

    while (geoHash.length < precision) {
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
            geoHash.append(base32Map[base32CharIndex])
            // Reset bits
            bits = 0
            base32CharIndex = 0
        }
    }
    return geoHash.toString()
}

/**
 * Receive a coordinate in hex format, determine the sign,
 * and transform it to a Double value.
 * @param coordinateHex coordinate value in HEX
 * @return double value
 */
fun calculateCoordinate(coordinateHex: String): Double {
    val coordinateBinary = hexToBinary(coordinateHex)
    if (coordinateBinary.startsWith("1")) {
        val longitudeStr = coordinateBinary.substring(1).toLong(2).toString()
        return -(
            longitudeStr.substring(0, 2) +
                "." +
                longitudeStr.substring(2)
        ).toDouble()
    }
    val longitudeStr = coordinateBinary.toLong(2).toString()
    return (
        longitudeStr.substring(0, 2) +
            "." +
            longitudeStr.substring(2)
    ).toDouble()
}

/**
 * Convert imei numberic ID to a string.
 * @param hexIMEI imei id represented in hexadecimal.
 */
fun convertIMEIToASCII(hexIMEI: String): String {
    return String(
        hexStringToByteArray(
            hexIMEI.substring(
                4,
            ),
        ),
    )
}

/**
 * Check if contains only numbers.
 * @param hexString
 */
fun isNumeric(hexString: String?): Boolean {
    if (hexString.isNullOrEmpty()) {
        return false
    }
    for (c in hexString.toCharArray()) {
        if (!Character.isDigit(c)) {
            return false
        }
    }
    return true
}

/**
 * Utility method when TPC/IP or UDP socket server are used to
 * check if the message represents a Teltonika deviceID.
 * @param hexIMEI the deviceID in hexadecimal.
 */
fun isIMEI(hexIMEI: String): Boolean {
    val imeiLength = hexIMEI.substring(0, 4).toInt(16)
    if (imeiLength != hexIMEI.substring(4).length / 2) {
        return false
    }
    val asciiIMEI = convertIMEIToASCII(hexIMEI)
    return isNumeric(asciiIMEI) && asciiIMEI.length == 15
}
