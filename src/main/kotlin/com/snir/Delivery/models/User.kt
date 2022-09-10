package com.snir.Delivery.models

import com.snir.Delivery.api.dto.UserDto

data class User(val name: String){

    companion object{
        fun toModel(dto: UserDto): User = User(name = dto.name)
    }
}
