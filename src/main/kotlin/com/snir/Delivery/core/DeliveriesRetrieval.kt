package com.snir.Delivery.core

import com.snir.Delivery.models.Delivery
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.util.*

object DeliveriesRetrieval {


    fun getTodayDeliveries(deliveries: Set<Delivery>): Set<Delivery> = deliveries.filter { isToday(it.timeslot.deliveryWindow.startingTime) }.toSet()

    fun getWeekDeliveries(deliveries: Set<Delivery>): Set<Delivery> = deliveries.filter { isThisWeek(it.timeslot.deliveryWindow.startingTime) }.toSet()

    private fun isToday(timeslotDateTime: LocalDateTime): Boolean = LocalDate.now() == timeslotDateTime.toLocalDate()

    private fun isThisWeek(timeslotDateTime: LocalDateTime): Boolean {
        val currentDay = Calendar.getInstance().get(Calendar.DAY_OF_WEEK)
        val endOfWeek = LocalDate.now().plusDays(7 - currentDay.toLong())

        return !(timeslotDateTime.isAfter(endOfWeek.atTime(LocalTime.MIDNIGHT)) || timeslotDateTime.isBefore( LocalDateTime.now()))
    }



}
