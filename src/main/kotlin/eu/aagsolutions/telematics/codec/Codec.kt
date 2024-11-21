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

import eu.aagsolutions.telematics.exceptions.CRCException
import org.slf4j.LoggerFactory

abstract class Codec<T>(private val data: String) : BaseCodec<T> {

    private val log = LoggerFactory.getLogger(Codec::class.java)

    protected fun getData(): String {
        return data
    }

    @Throws(CRCException::class)
    fun checkCrc() {
        val dataPartLengthCrc = data.substring(8, 16).toInt(16)
        val dataPartForCrcStr = data.substring(16, 16 + 2 * dataPartLengthCrc)
        val dataPartForCrc = hexStringToByteArray(dataPartForCrcStr)
        val crc = calculateCrc(dataPartForCrc)
        val crc16ArcFromRecord = data.substring(16 + dataPartForCrc.size * 2, 24 + dataPartForCrc.size * 2)
        if (!crc16ArcFromRecord.equals(bytesToHex(toBytes(4, crc)), ignoreCase = true)) {
            throw CRCException("Invalid CRC for $data")
        }
    }

    fun calculateCrc(dataPartForCrc: ByteArray): Int {
        var crc = 0
        for (b in dataPartForCrc) {
            crc = crc xor (b.toInt() and 0xFF)
            for (i in 0 until 8) {
                crc = if ((crc and 1) != 0) {
                    (crc shr 1) xor 0xA001
                } else {
                    crc shr 1
                }
            }
        }
        return crc
    }

    protected fun toBytes(length: Int, decimalValue: Int): ByteArray {
        val bytes = ByteArray(length)
        for (i in 0 until length) {
            bytes[length - 1 - i] = (decimalValue shr (i * 8) and 0xFF).toByte()
        }
        return bytes
    }
}
