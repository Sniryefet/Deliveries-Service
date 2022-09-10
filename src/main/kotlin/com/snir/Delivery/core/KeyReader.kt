package com.snir.Delivery.core

import java.io.File

object KeyReader {
    private val keyFile = File(".mykeys.txt")
    private const val GEO_KEY_ID = "GEOCODING"
    private const val HOLIDAYS_KEY_ID = "HOLIDAYS"


    fun getGeoKey(): String = getKey(GEO_KEY_ID)

    fun getHolidayKey(): String = getKey(HOLIDAYS_KEY_ID)

    private fun getKey(keyId: String): String{
        var key: String? = null

        keyFile.forEachLine { line ->
            val delimiter = '='
            val lineId = line.substringBefore(delimiter)
            if ( lineId == keyId){
                key = line.substringAfter(delimiter)
            }
        }

        return key ?: throw Exception("Could not find Geocoding API Key")
    }
}
