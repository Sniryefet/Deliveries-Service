package com.snir.Delivery.dao

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.snir.Delivery.handlers.HolidaysHandler
import com.snir.Delivery.models.Timeslot
import java.io.File


typealias TimeslotCapacity = Int
typealias TimeslotId = Int

object TimeslotsDao {
    private var incrementedId = 0
    private const val TIMES_LOT_CAPACITY_LIMIT = 2

    private val timeslots: MutableMap<TimeslotId, Pair<Timeslot, TimeslotCapacity>> by lazy {
        val timeslotsFile = File("src/main/resources/static/timeslots.json")
        val mapper = ObjectMapper()
        mapper.registerModule(JavaTimeModule())

        val timeslots = mapper.readValue(timeslotsFile, Array<Timeslot>::class.java)

        val holidayDates = HolidaysHandler.getHolidays().map { it.date }

        timeslots.filter { it.deliveryWindow.startingTime.toLocalDate() !in holidayDates }.associate { incrementedId++ to Pair(it, 0) }.toMutableMap()
    }

    fun getAllTimeslots(): Set<Timeslot> = timeslots.values.toMap().keys

    fun getTimeslotAndCap(timeslotId: TimeslotId): Pair<Timeslot, TimeslotId> = timeslots[timeslotId] ?: throw Exception("Could not find the timeslot in store")

    fun getAllOpenTimeslots(): Set<Timeslot> = timeslots.values.toMap().filter { (key, value) -> value < TIMES_LOT_CAPACITY_LIMIT }.keys

    fun getTimeslot(timeslotId: TimeslotId): Timeslot {
        val (timeslot, cap) = timeslots[timeslotId] ?: throw Exception("Could not find any Timeslot in store for the given Timeslot identifier")

        if (cap > 2) throw Exception("Timeslot capacity reached it's threshold of $TIMES_LOT_CAPACITY_LIMIT")

        return timeslot
    }

    fun updateTimeslotCap(timeslotId: TimeslotId): TimeslotId = if (!isAvailable(timeslotId)) throw Exception("Timeslot is invalid or the capacity is full , please try another timeslot")
    else {
        timeslots[timeslotId] = timeslots[timeslotId]!!.first to (timeslots[timeslotId]!!.second + 1)
        timeslotId
    }

    private fun isAvailable(timeslotId: TimeslotId): Boolean =
        timeslots[timeslotId]?.second?.let { it < TIMES_LOT_CAPACITY_LIMIT } ?: false


}
