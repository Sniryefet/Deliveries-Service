package com.snir.Delivery.models

import com.snir.Delivery.api.dto.DeliveryDto
import com.snir.Delivery.api.dto.DeliveryStatus
import com.snir.Delivery.dao.models.DeliveryDaoModel

data class Delivery(val deliveryStatus: DeliveryStatus, val timeslot: Timeslot){
    companion object{

        fun toModel(dto: DeliveryDto): Delivery = Delivery(deliveryStatus = dto.deliveryStatus, timeslot = Timeslot.toModel(dto.timeslotDto))

    }

    fun toDto(): DeliveryDto = DeliveryDto(deliveryStatus = deliveryStatus, timeslotDto = timeslot.toDto())

}