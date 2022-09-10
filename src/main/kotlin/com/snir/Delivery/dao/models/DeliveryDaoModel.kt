package com.snir.Delivery.dao.models

import com.snir.Delivery.api.dto.DeliveryStatus
import com.snir.Delivery.dao.TimeslotsDao
import com.snir.Delivery.models.Delivery
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "deliveries")
class DeliveryDaoModel(@Column val user: String, @Column var deliveryStatus: String, @Column val timeslotId: Int) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var deliveryId: Long = -1
        get() = field
        set(value) {
            field = value
        }



    fun toModel(): Delivery {
        return Delivery(deliveryStatus = DeliveryStatus.fromDbName(deliveryStatus), TimeslotsDao.getTimeslot(timeslotId))
    }
}
