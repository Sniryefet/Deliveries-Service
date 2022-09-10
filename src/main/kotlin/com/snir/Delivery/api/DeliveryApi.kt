package com.snir.Delivery.api

import com.snir.Delivery.api.dto.*

interface DeliveryApi {

    fun resolveAddress(oneLineAddress: String): AddressDto

    fun getAvailableTimeslot(addressDto: AddressDto): List<TimeslotDto>

    fun bookDelivery(deliveryRequest: DeliveryRequest)

    fun completeDelivery(deliveryId: Long)

    fun getTodayDeliveries(): List<DeliveryDto>

    fun getWeekDeliveries(): List<DeliveryDto>

    fun cancelDelivery(deliveryId: Long)
}
