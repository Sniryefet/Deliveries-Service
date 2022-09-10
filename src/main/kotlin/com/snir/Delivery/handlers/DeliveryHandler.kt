package com.snir.Delivery.handlers

import com.snir.Delivery.api.dto.DeliveryStatus
import com.snir.Delivery.core.BusinessDeliveryCapacity
import com.snir.Delivery.core.DeliveriesRetrieval
import com.snir.Delivery.core.OpenTimeslots
import com.snir.Delivery.dao.DeliveriesDao
import com.snir.Delivery.dao.TimeslotsDao
import com.snir.Delivery.dao.models.DeliveryDaoModel
import com.snir.Delivery.models.Address
import com.snir.Delivery.models.Delivery
import com.snir.Delivery.models.Timeslot
import com.snir.Delivery.models.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.time.LocalDate


@Service
class DeliveryHandler(@Autowired private val deliveriesDao: DeliveriesDao, @Autowired private val geocodingHandler: GeocodingHandler) {

    fun resolveAddress(oneLineAddress: String): Address = geocodingHandler.getStructuredAddress(oneLineAddress)

    fun getAvailableTimeslot(address: Address): Set<Timeslot> {
        return OpenTimeslots.getOpenTimeslots(TimeslotsDao.getAllOpenTimeslots(), address)
    }

    @Synchronized
    fun bookDelivery(user: User, timeslotId: Int) {

       if (BusinessDeliveryCapacity.isCapacityReached(timeslotId)) throw Exception ("Cannot book delivery for the given timeslot since the capacity of deliveries for the date reached, please choose another timeslot with different date")

        deliveriesDao.save(
            DeliveryDaoModel(
               user = user.name,
               deliveryStatus = DeliveryStatus.WAITING_TO_BE_DELIVERED.name,
               timeslotId = TimeslotsDao.updateTimeslotCap(timeslotId)
            )
        )
    }

    fun completeDelivery(deliveryId: Long) {
       val delivery = deliveriesDao.findByIdOrNull(deliveryId) ?: throw Exception("Could not find the delivery in store")

        delivery.deliveryStatus = DeliveryStatus.DELIVERED.name

        deliveriesDao.save(delivery)
    }

    fun getTodayDeliveries(): Set<Delivery> {
        val deliveriesDbModel = deliveriesDao.findAll()
        val deliveries = deliveriesDbModel.map { it.toModel() }.toSet()

        return DeliveriesRetrieval.getTodayDeliveries(deliveries)
    }

    fun getWeekDeliveries(): Set<Delivery> {
        val deliveriesDbModel = deliveriesDao.findAll()
        val deliveries = deliveriesDbModel.map { it.toModel() }.toSet()

        return DeliveriesRetrieval.getWeekDeliveries(deliveries)
    }

    fun cancelDelivery(deliveryId: Long) {
        deliveriesDao.deleteById(deliveryId)
    }



}