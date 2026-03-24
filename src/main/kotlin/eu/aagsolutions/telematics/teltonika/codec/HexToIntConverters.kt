package eu.aagsolutions.telematics.teltonika.codec

import java.math.BigDecimal
import java.nio.ByteBuffer
import java.nio.ByteOrder

private const val HEX_RADIX = 16
private const val DEFAULT_PADDING = 4

/**
 * Converts a hexadecimal string to an integer.
 * @param data hexadecimal string to convert
 */
fun signedNoMultiply(data: String): Int {
    val hexPadded = data.padStart(DEFAULT_PADDING, '0')
    val binary = hexStringToByteArray(hexPadded)
    return ByteBuffer
        .wrap(binary)
        .order(ByteOrder.BIG_ENDIAN)
        .short
        .toInt()
}

/**
 * Converts a hexadecimal string to a BigDecimal with a multiplier of 0.1.
 * @param data hexadecimal string to convert
 */
fun multiplyInt01(data: String): BigDecimal = BigDecimal(data.toInt(HEX_RADIX)).multiply(BigDecimal("0.1"))

/**
 * Converts a hexadecimal string to a BigDecimal with a multiplier of 0.01.
 * @param data hexadecimal string to convert
 */
fun multiplyInt001(data: String): BigDecimal = BigDecimal(data.toInt(HEX_RADIX)).multiply(BigDecimal("0.01"))

/**
 * Converts a hexadecimal string to a BigDecimal with a multiplier of 0.001.
 * @param data hexadecimal string to convert
 */
fun multiplyInt0001(data: String): BigDecimal = BigDecimal(data.toInt(HEX_RADIX)).multiply(BigDecimal("0.001"))

/**
 * Converts a hexadecimal string to an integer.
 * @param data hexadecimal string to convert
 */
fun parseInt(data: String): Int = data.toInt(HEX_RADIX)
