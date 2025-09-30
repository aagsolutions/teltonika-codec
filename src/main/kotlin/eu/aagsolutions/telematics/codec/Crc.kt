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

import eu.aagsolutions.telematics.exceptions.CRCException

private const val BIT_MASK = 0xFF
private const val CRC_16_BIT_MASK = 0xA001
private const val BITS_PER_BYTE = 8
private const val CRC_START_LENGTH = 8
private const val CRC_END_LENGTH = 16
private const val HEX_RADIX = 16
private const val CRC_HEADER_LENGTH = 24
private const val CRC_CHECK_LENGTH = 4

/**
 * Check if message CRC is valid.
 * @param encodedData
 * @throws CRCException
 */
@Throws(CRCException::class)
fun checkCrc(encodedData: String) {
    val dataPartLengthCrc = encodedData.substring(CRC_START_LENGTH, CRC_END_LENGTH).toInt(HEX_RADIX)
    val dataPartForCrcStr = encodedData.substring(CRC_END_LENGTH, CRC_END_LENGTH + 2 * dataPartLengthCrc)
    val dataPartForCrc = hexStringToByteArray(dataPartForCrcStr)
    val crc = calculateCrc(dataPartForCrc)
    val crc16ArcFromRecord = encodedData.substring(CRC_END_LENGTH + dataPartForCrc.size * 2, CRC_HEADER_LENGTH + dataPartForCrc.size * 2)
    if (!crc16ArcFromRecord.equals(bytesToHex(toBytes(CRC_CHECK_LENGTH, crc)), ignoreCase = true)) {
        throw CRCException("Invalid CRC for $encodedData")
    }
}

/**
 * Calculate @see <a href="https://wiki.teltonika-gps.com/view/Codec#CRC-16">CRC-16</a>.
 * @param dataPartForCrc the byte array from the message used for CRC calculation
 */
fun calculateCrc(dataPartForCrc: ByteArray): Int {
    var crc = 0
    for (b in dataPartForCrc) {
        crc = crc xor (b.toInt() and BIT_MASK)
        for (i in 0 until BITS_PER_BYTE) {
            crc =
                if ((crc and 1) != 0) {
                    (crc shr 1) xor CRC_16_BIT_MASK
                } else {
                    crc shr 1
                }
        }
    }
    return crc
}

fun toBytes(
    length: Int,
    decimalValue: Int,
): ByteArray {
    val bytes = ByteArray(length)
    for (i in 0 until length) {
        bytes[length - 1 - i] = (decimalValue shr (i * BITS_PER_BYTE) and BIT_MASK).toByte()
    }
    return bytes
}
