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
import eu.aagsolutions.telematics.model.PermanentIO
import eu.aagsolutions.telematics.model.Telemetry

/**
 * Class for decoding/encoding CODEC8 and CODEC8E.
 * The main difference between the codecs is the iteration step length,
 * and also CODEC8E can have more data.
 */
class Codec8(data: String) : Codec<List<Telemetry>>(data) {

    private val HEXADECIMAL_NR: Int = 16
    private val DATA_LENGTHS = intArrayOf(2, 4, 8, HEXADECIMAL_NR)

    private val CODEC_ID_8_STEP = 2

    private val CODEC_ID_8E_STEP = 4
    private val NUMBER_OF_RECORDS_STEP = 2
    private val DATA_FIELD_LENGTH_STEP = 8
    private val DATA_END_SUBTRACT = 6
    private val CODEC_ID_8 = 8
    private val CODEC_ID_8E = 142
    private val AVL_DATA_START_INDEX = 20
    private val CODEC_ID_START_INDEX = HEXADECIMAL_NR
    private val NR_OF_RECORDS_START_INDEX = 18
    private val DATA_FIELD_LENGTH_START_INDEX = 8

    override fun decode(): List<Telemetry> {
        val codecId =
            getData().substring(CODEC_ID_START_INDEX, CODEC_ID_START_INDEX + CODEC_ID_8_STEP).toInt(HEXADECIMAL_NR)
        if (codecId != CODEC_ID_8E && codecId != CODEC_ID_8) {
            throw CRCException("Invalid codec")
        }
        val dataStep = if (codecId == CODEC_ID_8) CODEC_ID_8_STEP else CODEC_ID_8E_STEP
        this.checkCrc()
        val numberOfRecords =
            getData().substring(NR_OF_RECORDS_START_INDEX, NR_OF_RECORDS_START_INDEX + NUMBER_OF_RECORDS_STEP)
                .toInt(HEXADECIMAL_NR)
        val dataFieldLength =
            getData().substring(DATA_FIELD_LENGTH_START_INDEX, DATA_FIELD_LENGTH_START_INDEX + DATA_FIELD_LENGTH_STEP)
                .toInt(HEXADECIMAL_NR)
        val avlDataStart = getData().substring(AVL_DATA_START_INDEX)
        var dataFieldPosition = 0

        val dataEnd = 2 * dataFieldLength - DATA_END_SUBTRACT
        val eventTimestamp = System.currentTimeMillis()
        val data: MutableList<Telemetry> = ArrayList()
        while (dataFieldPosition < dataEnd) {
            val timestampHex = avlDataStart.substring(dataFieldPosition, dataFieldPosition + HEXADECIMAL_NR)
            dataFieldPosition += timestampHex.length
            val priorityHex = avlDataStart.substring(dataFieldPosition, dataFieldPosition + 2)
            dataFieldPosition += priorityHex.length
            val longitudeHex = avlDataStart.substring(dataFieldPosition, dataFieldPosition + 8)
            dataFieldPosition += longitudeHex.length
            val latitudeHex = avlDataStart.substring(dataFieldPosition, dataFieldPosition + 8)
            dataFieldPosition += latitudeHex.length
            val altitudeHex = avlDataStart.substring(dataFieldPosition, dataFieldPosition + 4)
            dataFieldPosition += altitudeHex.length
            val angleHex = avlDataStart.substring(dataFieldPosition, dataFieldPosition + 4)
            dataFieldPosition += angleHex.length
            val satellitesHex = avlDataStart.substring(dataFieldPosition, dataFieldPosition + 2)
            dataFieldPosition += satellitesHex.length
            val gpsSpeedHex = avlDataStart.substring(dataFieldPosition, dataFieldPosition + 4)
            dataFieldPosition += gpsSpeedHex.length
            val eventIdHex = avlDataStart.substring(dataFieldPosition, dataFieldPosition + dataStep)
            dataFieldPosition += eventIdHex.length
            val longitude = calculateCoordinate(longitudeHex)
            val latitude = calculateCoordinate(latitudeHex)
            val geoHash = encodeGeoHash(latitude, longitude)
            val permanentIO = PermanentIO(
                timestampHex.toLong(HEXADECIMAL_NR),
                priorityHex.toShort(HEXADECIMAL_NR),
                geoHash,
                altitudeHex.toInt(HEXADECIMAL_NR),
                angleHex.toInt(HEXADECIMAL_NR),
                satellitesHex.toInt(HEXADECIMAL_NR),
                gpsSpeedHex.toInt(HEXADECIMAL_NR),
                eventIdHex.toInt(HEXADECIMAL_NR),
            )
            val totalIoElements = avlDataStart.substring(dataFieldPosition, dataFieldPosition + dataStep)
            dataFieldPosition += totalIoElements.length
            val eventualIoValues = HashMap<Int, String>()
            for (dataLength in DATA_LENGTHS) {
                val byteIoNumber = avlDataStart.substring(dataFieldPosition, dataFieldPosition + dataStep)
                val byteIoNumberParsed = byteIoNumber.toInt(HEXADECIMAL_NR)
                dataFieldPosition += byteIoNumber.length

                for (i in 0..<byteIoNumberParsed) {
                    val key = avlDataStart.substring(dataFieldPosition, dataFieldPosition + dataStep)
                    dataFieldPosition += key.length
                    val value = avlDataStart.substring(dataFieldPosition, dataFieldPosition + dataLength)
                    dataFieldPosition += value.length
                    val keyValue: Int = key.toInt(HEXADECIMAL_NR)
                    eventualIoValues[keyValue] = value
                }
            }
            if (codecId == CODEC_ID_8E) {
                val byteIoNumber = avlDataStart.substring(dataFieldPosition, dataFieldPosition + dataStep)
                dataFieldPosition += byteIoNumber.length
            }
            data.add(Telemetry(eventTimestamp, permanentIO, eventualIoValues))
        }
        return data
    }

    override fun encode(): String {
        TODO("Not yet implemented")
    }
}