package com.snir.Delivery.api.dto

import java.time.LocalDateTime

data class TimeslotDto(val deliveryWindow: DeliveryWindowDto, val supportedAddresses: Set<AddressDto>)


data class DeliveryWindowDto(val startingTime: LocalDateTime, val endTime: LocalDateTime){

    init {
        if (startingTime.isAfter(endTime) || !isSameDate()) {
            throw Exception("Starting time must be before the end time and Timeslot window must start and end on the same day")
        }

    }

    private fun isSameDate(): Boolean{
        val startingTimeLocalDate = startingTime.toLocalDate()
        val endingTimeLocalDate = endTime.toLocalDate()
        return startingTimeLocalDate.year == endingTimeLocalDate.year && startingTimeLocalDate.month == endingTimeLocalDate.month && startingTimeLocalDate.dayOfMonth == endingTimeLocalDate.dayOfMonth
    }

}
