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

import eu.aagsolutions.telematics.model.CmdResponse
import kotlin.test.Test
import kotlin.test.assertEquals

class Codec12DecoderTest {
    @Test
    fun `it should succeful decode getvin cmd`() {
        val inputData = "000000000000000e0c01050000000667657476696e010000670a"
        val codec = Codec12Decoder(inputData, "defaultImei")
        val decoded = codec.decode()
        val expected = CmdResponse("defaultImei", "getvin")
        assertEquals(expected, decoded)
    }

    @Test
    fun `it should successful decode getio cmd response`() {
        val inputData =
            "00000000000000370C01060000002F44493" +
                "13A31204449323A30204449333A302041494" +
                "E313A302041494E323A313639323420444F31" +
                "3A3020444F323A3101000066E3"
        val codec = Codec12Decoder(inputData, "defaultImei")
        val decoded = codec.decode()
        val expected = CmdResponse("defaultImei", "DI1:1 DI2:0 DI3:0 AIN1:0 AIN2:16924 DO1:0 DO2:1")
        assertEquals(expected, decoded)
    }

    @Test
    fun `it should successful decode getinfo cmd response`() {
        val inputData =
            "00000000000000900C010600000088494E493A323031392F372F323" +
                "220373A3232205254433A323031392F372F323220373A353320525" +
                "3543A32204552523A312053523A302042523A302043463A3020464" +
                "73A3020464C3A302054553A302F302055543A3020534D533A30204" +
                "E4F4750533A303A3330204750533A31205341543A302052533A332" +
                "052463A36352053463A31204D443A30010000C78F"
        val codec = Codec12Decoder(inputData, "defaultImei")
        val decoded = codec.decode()
        val expected =
            CmdResponse(
                "defaultImei",
                "INI:2019/7/22 7:22 RTC:2019/7/22 7:53 RST:2 ERR:1 SR:0 BR:0 CF:0 " +
                    "FG:0 FL:0 TU:0/0 UT:0 SMS:0 NOGPS:0:30 GPS:1 SAT:0 RS:3 RF:65 " +
                    "SF:1 MD:0",
            )
        assertEquals(expected, decoded)
    }
}
