package com.snir.Delivery.api.dto

data class DeliveryDto(val deliveryStatus: DeliveryStatus, val timeslotDto: TimeslotDto)

data class DeliveryRequest(val user: String, val timeslotId: Int)

enum class DeliveryStatus{
    DELIVERED,
    WAITING_TO_BE_DELIVERED;

    companion object{

        fun fromDbName(dbNameStr: String): DeliveryStatus =
            DeliveryStatus.values().single { it.name == dbNameStr }

    }



}
