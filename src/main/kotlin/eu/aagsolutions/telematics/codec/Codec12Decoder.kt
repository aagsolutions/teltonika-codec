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
import eu.aagsolutions.telematics.model.CmdResponse

/**
 * Codec12 decoder.
 */
class Codec12Decoder(data: String, deviceId: String) : Decoder<CmdResponse>(data, deviceId) {
    @Throws(CRCException::class)
    override fun decode(): CmdResponse {
        val codecId = getData().substring(16, 18).toInt(16)
        if (codecId != 12) {
            throw CRCException("Invalid codec")
        }
        checkCrc(getData())
        val dataSize = getData().substring(22, 30).toInt(16)
        val rsp = hexStringToByteArray(getData().substring(30, 30 + dataSize * 2))
        return CmdResponse(getDeviceId(), String(rsp, Charsets.UTF_8))
    }
}
