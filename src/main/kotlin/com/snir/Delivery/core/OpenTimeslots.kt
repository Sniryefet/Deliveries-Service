package com.snir.Delivery.core

import com.snir.Delivery.models.Address
import com.snir.Delivery.models.Timeslot

object OpenTimeslots {

    fun getOpenTimeslots(timeslots: Set<Timeslot>, targetAddress: Address): Set<Timeslot> = timeslots.filter { timeslot -> targetAddress in  timeslot.supportedAddresses}.toSet()
}