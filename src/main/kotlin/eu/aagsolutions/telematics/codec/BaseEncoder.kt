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
 * A generic interface for encoding data into a specific hexadecimal format.
 * Implementations of this interface are responsible for converting data of type T
 * into an encoded format that includes a device identifier and a hexadecimal string representation of the data.
 *
 * @param T the type of the input data to be encoded
 */
interface BaseEncoder<T> {
    /**
     * Encodes the given data and device identifier into a hexadecimal representation.
     *
     * @param data the input data to be encoded
     * @param deviceId the unique identifier of the device
     * @return an instance of Encoded containing the device identifier and the encoded hexadecimal data
     */
    fun encode(
        data: T,
        deviceId: String,
    ): Encoded
}
