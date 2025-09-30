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
import eu.aagsolutions.telematics.model.CmdResponse

/**
 * Decodes messages encoded with Codec12 format.
 *
 * This class implements the `BaseDecoder` interface for decoding Codec12
 * formatted hexadecimal messages into `CmdResponse` objects. It validates
 * the message using CRC checks and extracts the necessary information.
 *
 * The decoding process includes:
 * - Verifying the Codec ID (must be 12).
 * - Performing a CRC validation on the message.
 * - Extracting the response payload and reconstructing it as a string.
 *
 * @throws CRCException if the message's CRC is invalid or the Codec ID doesn't match.
 */
class Codec12Decoder : BaseDecoder<CmdResponse> {
    @Suppress("MagicNumber")
    @Throws(CRCException::class)
    override fun decode(
        data: String,
        deviceId: String,
    ): CmdResponse {
        val codecId = data.substring(16, 18).toInt(16)
        if (codecId != 12) {
            throw CRCException("Invalid codec")
        }
        checkCrc(data)
        val dataSize = data.substring(22, 30).toInt(16)
        val rsp = hexStringToByteArray(data.substring(30, 30 + dataSize * 2))
        return CmdResponse(deviceId, String(rsp, Charsets.UTF_8))
    }
}
