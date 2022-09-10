package com.snir.Delivery.models

import com.fasterxml.jackson.annotation.JsonProperty
import com.snir.Delivery.api.dto.AddressDto

data class Address(@JsonProperty("street") val street: String,@JsonProperty("addressLine1")  val addressLine1: String,@JsonProperty("addressLine2")  val addressLine2: String,@JsonProperty("country")  val country: String,@JsonProperty("postcode")  val postcode: String){


    companion object{

        fun toModel(dto: AddressDto): Address = Address(street = dto.street, addressLine1 = dto.addressLine1, addressLine2 = dto.addressLine2, country = dto.country, postcode = dto.postcode)

    }

    fun toDto(): AddressDto = AddressDto(street = street, addressLine1 = addressLine1, addressLine2 = addressLine2, country = country, postcode = postcode)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other?.javaClass != javaClass) return false

        other as Address

        return this.street == other.street && this.addressLine1 == other.addressLine1 && this.country == other.country && this.postcode == other.postcode
    }
}
