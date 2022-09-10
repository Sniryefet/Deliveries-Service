package com.snir.Delivery.resource

import com.snir.Delivery.api.DeliveryApi
import com.snir.Delivery.api.dto.*
import com.snir.Delivery.handlers.DeliveryHandler
import com.snir.Delivery.models.Address
import com.snir.Delivery.models.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@Service
@RequestMapping("/delivery")
class DeliveryResource( @Autowired private val deliveryHandler: DeliveryHandler): DeliveryApi {



    @PostMapping("/resolve_address")
    override fun resolveAddress(@RequestBody oneLineAddress: String): AddressDto =
        deliveryHandler.resolveAddress(oneLineAddress).toDto()


    @PostMapping("/timeslots")
    override fun getAvailableTimeslot(@RequestBody addressDto: AddressDto): List<TimeslotDto> {
        val address = Address.toModel(addressDto)

        val availableTimeslots = deliveryHandler.getAvailableTimeslot(address)

        return availableTimeslots.map { it.toDto() }

    }

    @PostMapping("/deliveries")
    override fun bookDelivery( @RequestBody deliveryRequest: DeliveryRequest) {
        val user = User(deliveryRequest.user)
        deliveryHandler.bookDelivery(user,deliveryRequest.timeslotId)
    }

    @PostMapping("/deliveries/{id}/complete")
    override fun completeDelivery(@PathVariable id: Long) {
        deliveryHandler.completeDelivery(id)
    }

    @GetMapping("/deliveries/daily")
    override fun getTodayDeliveries(): List<DeliveryDto> {
        val todayDeliveries = deliveryHandler.getTodayDeliveries()

        return todayDeliveries.map { it.toDto() }
    }

    @GetMapping("/deliveries/weekly")
    override fun getWeekDeliveries(): List<DeliveryDto> {
        val weeklyDeliveries = deliveryHandler.getWeekDeliveries()

        return weeklyDeliveries.map { it.toDto() }
    }

    @DeleteMapping("/deliveries/{id}")
    override fun cancelDelivery(@PathVariable id: Long) {
        deliveryHandler.cancelDelivery(id)
    }


}