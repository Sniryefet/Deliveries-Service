package com.snir.Delivery.core

import com.snir.Delivery.dao.TimeslotId
import com.snir.Delivery.dao.TimeslotsDao
import java.time.LocalDate


object BusinessDeliveryCapacity {
    private const val BUSINESS_DELIVERY_CAPACITY = 10


    fun isCapacityReached(timeslotId: TimeslotId): Boolean{
        val map = mutableMapOf<LocalDate, Int>()
        val timeslots = TimeslotsDao.getAllTimeslots()
        timeslots.forEach { timeslot ->
            val date = timeslot.deliveryWindow.endTime.toLocalDate()
            map[date] = map[date]?.let { it + 1 } ?: 0
        }

        val timeslot = TimeslotsDao.getTimeslotAndCap(timeslotId).first
        val date = timeslot.deliveryWindow.endTime.toLocalDate()
        map[date] = map[date]?.let { it + 1 } ?: 0

        return map.values.any { it > 10}
    }

}