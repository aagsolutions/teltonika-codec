# Teltonika telematic devices coder/decoder

![Build&Test](https://github.com/atdi/teltonika-codec/actions/workflows/main.yml/badge.svg) [![Maven Central](https://img.shields.io/maven-central/v/eu.aagsolutions.telematics/teltonika-codec)](https://central.sonatype.com/artifact/eu.aagsolutions.telematics/teltonika-codec/1.0.0) [![GitHub license](https://img.shields.io/badge/license-MIT-green.svg?style=flat)](https://opensource.org/licenses/MIT)

## Introduction

Teltonika is one of the leaders on Europeam market for HW devices used for vehicle telematics.
Their devices are pretty nice and quite performant.

This library represents an opensource implementation for encoding/decoding [Teltonika CODEC8, CODEC8ex and CODEC12](https://wiki.teltonika-gps.com/view/Codec).

Because this library is generic, the output won't contain the result fully decoded and will perform the mapping between the AVL ID and the hexadecimal value of the parameter.
For example for FMC130 if you will have in your map the element 81: "3C", this means that the speed from CAN bus is 60 km/h, and you should converted to decimal.

The common values for all devices, are fully converted.
Those values are:
- timestamp: Long
- priority: Short
- geoHash: String (the geoHash is actually internally converted from lat/lon pair by the library).
- altitude: Int
- angle: Int
- satellites: Int
- gpsSpeed: Int
- eventId: Int
 

## How to use it

Gradle:
```kotlin
implementation("eu.aagsolutions.telematics:teltonika-codec:1.0.0")
```

Maven:
```xml
<dependency>
    <groupId>eu.aagsolutions.telematics</groupId>
    <artifactId>teltonika-codec</artifactId>
    <version>1.0.0</version>
</dependency>
```

For a complete example please have a look to following demo project using TCP/IP sockets:
[Teltonika demo TCP/IP server](https://github.com/atdi/teltonika-demo-server)

