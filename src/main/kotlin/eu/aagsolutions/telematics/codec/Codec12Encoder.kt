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

import eu.aagsolutions.telematics.model.Encoded

/**
 * A specialized implementation of the `BaseEncoder` interface for encoding data using the Codec 12 protocol.
 * This encoder processes a given string into an encoded message in hexadecimal format, adhering to the
 * structure defined by the Codec 12 specification.
 *
 * The encoding process transforms input data into a hexadecimal message that includes metadata about the
 * command's size, data payload structure, and CRC for message integrity verification.
 *
 * The resulting message follows the structure:
 * - Header
 * - Data size
 * - Payload (command data)
 * - CRC checksum
 *
 * The encoded output contains all necessary components to validate and process the data by compatible clients.
 */
class Codec12Encoder : BaseEncoder<String> {
    /**
     * Encodes the given data and device ID into an encoded hexadecimal message according to the Codec 12 protocol.
     *
     * @param data the string data to be encoded
     * @param deviceId the unique identifier of the target device for which the data is encoded
     * @return an instance of the Encoded class containing the device ID and the encoded hexadecimal message
     */
    override fun encode(
        data: String,
        deviceId: String,
    ): Encoded {
        val cmd = data.toByteArray(Charsets.UTF_8)
        val cmdSize = bytesToHex(toBytes(4, cmd.size))
        val dataSize = bytesToHex(toBytes(4, 1 + 1 + 1 + 4 + cmd.size + 1))
        val completeData = "0C0105${cmdSize}${bytesToHex(data.toByteArray(Charsets.UTF_8))}01"
        val crc = calculateCrc(hexStringToByteArray(completeData))
        val completeMsgHex = "00000000${dataSize}${completeData}${bytesToHex(toBytes(4, crc))}"
        return Encoded(deviceId, completeMsgHex.uppercase())
    }
}
