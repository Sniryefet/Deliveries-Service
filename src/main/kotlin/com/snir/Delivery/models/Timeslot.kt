package com.snir.Delivery.models

import com.fasterxml.jackson.annotation.JsonProperty
import com.snir.Delivery.api.dto.DeliveryWindowDto
import com.snir.Delivery.api.dto.TimeslotDto
import java.time.LocalDateTime

data class Timeslot( @JsonProperty("deliveryWindow") val deliveryWindow: DeliveryWindow, @JsonProperty("supportedAddresses") val supportedAddresses: Set<Address>){
    companion object{

        fun toModel(dto: TimeslotDto): Timeslot = Timeslot(deliveryWindow = DeliveryWindow.toModel(dto.deliveryWindow), supportedAddresses = dto.supportedAddresses.map { Address.toModel(it) }.toSet())
    }

    fun toDto(): TimeslotDto = TimeslotDto(deliveryWindow = deliveryWindow.toDto(), supportedAddresses = supportedAddresses.map { it.toDto() }.toSet())
}


data class DeliveryWindow(@JsonProperty("startingTime")  val startingTime: LocalDateTime, @JsonProperty("endTime") val endTime: LocalDateTime){

    companion object{

        fun toModel(dto: DeliveryWindowDto): DeliveryWindow = DeliveryWindow(startingTime = dto.startingTime, endTime = dto.endTime)
    }

    fun toDto(): DeliveryWindowDto = DeliveryWindowDto(startingTime = startingTime, endTime = endTime)
}
