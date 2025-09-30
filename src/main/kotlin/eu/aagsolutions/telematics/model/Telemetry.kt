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

package eu.aagsolutions.telematics.model

/**
 * Represents telemetry data associated with a specific device and event.
 *
 * This class serves as the main structure for capturing telemetry information, including
 * device identification, event timing, permanent input/output data, and additional event-specific
 * data points.
 *
 * @property deviceId The unique identifier of the device associated with the telemetry data.
 * @property eventTimestamp The timestamp of the event in milliseconds.
 * @property permanentIO Permanent input/output data associated with the telemetry event.
 * @property data A map containing additional event-specific data, where the key represents
 * the data identifier and the value represents its corresponding value as a string.
 */
data class Telemetry(
    var deviceId: String,
    var eventTimestamp: Long,
    var permanentIO: PermanentIO,
    var data: Map<Int, String>,
)
