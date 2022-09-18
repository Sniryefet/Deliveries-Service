package com.snir.Delivery.handlers

import com.fasterxml.jackson.databind.ObjectMapper
import com.snir.Delivery.core.KeyReader
import com.snir.Delivery.models.Address
import org.springframework.stereotype.Service
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder

@Service
class GeocodingHandler {
    private val key: String by lazy {
        KeyReader.getGeoKey()
    }


    fun getStructuredAddress(oneLineAddress: String): Address {
        val textAddressAsUrl = URLEncoder.encode(oneLineAddress)
        val url = URL("https://api.geoapify.com/v1/geocode/search?text=$textAddressAsUrl$&format=json&apiKey=$key")
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

        val res = ObjectMapper().readTree(json.toString())["results"].first()

        return Address(
            street = res.get("street").asText(),
            addressLine1 = res.get("address_line1").asText(),
            addressLine2 = res.get("address_line2").asText() ,
            country = res.get("country").asText() ,
            postcode = res.get("postcode").asText()
        )

    }

}