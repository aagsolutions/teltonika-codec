package eu.aagsolutions.telematics.codec

import kotlin.test.Test
import kotlin.test.assertEquals

class Codec12Test {

    @Test
    fun shouldSuccessfulEncodeGetInfoCmd() {
        val codec = Codec12("getinfo")
        val encoded = codec.encode()
        val expected = "000000000000000F0C010500000007676574696E666F0100004312"
        assertEquals(expected, encoded)
    }

    @Test
    fun shouldSuccessfulEncodeGetVinCmd() {
        val codec = Codec12("getvin")
        val encoded = codec.encode()
        val expected = "000000000000000e0c01050000000667657476696e010000670a".uppercase()
        assertEquals(expected, encoded)
    }

    @Test
    fun shouldSuccefulDecodeGetVinCmd() {
        val inputData = "000000000000000e0c01050000000667657476696e010000670a"
        val codec = Codec12(inputData)
        val decoded = codec.decode()
        val expected = "getvin"
        assertEquals(expected, decoded)
    }

    @Test
    fun shouldSuccessfulDecodeGetIoRsp() {
        val inputData = "00000000000000370C01060000002F44493" +
                "13A31204449323A30204449333A302041494" +
                "E313A302041494E323A313639323420444F31" +
                "3A3020444F323A3101000066E3"
        val codec = Codec12(inputData)
        val decoded = codec.decode()
        val expected = "DI1:1 DI2:0 DI3:0 AIN1:0 AIN2:16924 DO1:0 DO2:1"
        assertEquals(expected, decoded)
    }

    @Test
    fun shouldSuccessfulDecodeGetInfoCmd() {
        val inputData = "000000000000000F0C010500000007676574696E666F0100004312"
        val codec = Codec12(inputData)
        val decoded = codec.decode()
        val expected = "getinfo"
        assertEquals(expected, decoded)
    }

    @Test
    fun shouldSuccessfulDecodeGetInfoRsp() {
        val inputData = "00000000000000900C010600000088494E493A323031392F372F323" +
                "220373A3232205254433A323031392F372F323220373A353320525" +
                "3543A32204552523A312053523A302042523A302043463A3020464" +
                "73A3020464C3A302054553A302F302055543A3020534D533A30204" +
                "E4F4750533A303A3330204750533A31205341543A302052533A332" +
                "052463A36352053463A31204D443A30010000C78F"
        val codec = Codec12(inputData)
        val decoded = codec.decode()
        val expected = "INI:2019/7/22 7:22 RTC:2019/7/22 7:53 RST:2 ERR:1 SR:0 BR:0 CF:0 " +
                "FG:0 FL:0 TU:0/0 UT:0 SMS:0 NOGPS:0:30 GPS:1 SAT:0 RS:3 RF:65 " +
                "SF:1 MD:0"
        assertEquals(expected, decoded)
    }
}
