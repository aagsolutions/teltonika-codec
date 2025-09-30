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
 * Represents permanent input/output data associated with a telemetry event.
 *
 * This class contains data elements that can be found in telemetry events for all
 * Teltonika devices and describes various properties such as location, speed, and priority.
 *
 * @property timestamp The timestamp of the event in milliseconds.
 * @property priority The priority of the event, represented as a short value.
 * @property geoHash The geohash representing the approximate location of the event.
 * @property altitude The altitude at which the event took place, represented in meters.
 * @property angle The direction or bearing angle of the event, typically in degrees.
 * @property satellites The number of satellites available during the event.
 * @property gpsSpeed The GPS-calculated speed during the event, represented in km/h.
 * @property eventId The unique identifier for the specific event.
 */
data class PermanentIO(
    var timestamp: Long,
    var priority: Short,
    var geoHash: String,
    var altitude: Int,
    var angle: Int,
    var satellites: Int,
    var gpsSpeed: Int,
    var eventId: Int,
)
