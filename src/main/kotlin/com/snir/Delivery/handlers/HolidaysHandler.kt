package com.snir.Delivery.handlers

import com.fasterxml.jackson.databind.ObjectMapper
import com.snir.Delivery.core.KeyReader
import com.snir.Delivery.models.Holiday
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.time.LocalDate

object HolidaysHandler {
    private val key: String by lazy {
        KeyReader.getHolidayKey()
    }

    fun getHolidays(): List<Holiday> {
        val url = URL("https://holidayapi.com/v1/holidays?pretty&key=$key&country=IL&year=2021") // Free accounts are limited to last year's historical data only (therefore, the hardcoded year)
        val http: HttpURLConnection = url.openConnection() as HttpURLConnection
        http.setRequestProperty("Accept", "application/json")

        val reader = BufferedReader(
            InputStreamReader(http.getInputStream())
        )
        var inputLine: String?
        val json = StringBuffer()
        while (reader.readLine().also { inputLine = it } != null) {
            json.append(inputLine)
        }
        reader.close()
        http.disconnect()

        return ObjectMapper().readTree(json.toString()).get("holidays").map { Holiday(it.get("name").asText(), LocalDate.parse(it.get("date").asText())) }

    }

}


fun main() {

    val res = HolidaysHandler.getHolidays()

    val stop =0
}

// 2021-2-25