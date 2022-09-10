package com.snir.Delivery.dao

import com.snir.Delivery.dao.models.DeliveryDaoModel
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository


@Repository
interface DeliveriesDao: JpaRepository<DeliveryDaoModel, Long>
