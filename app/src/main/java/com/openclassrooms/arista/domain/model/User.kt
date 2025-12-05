package com.openclassrooms.arista.domain.model

import com.openclassrooms.arista.data.entity.UserDto

data class User(
    val id: Long? = null,
    val name: String,
    val email: String,
    val password: String
) {
    fun toDto() = UserDto(
        id = this.id ?: 0,
        name = this.name,
        email = this.email,
        password = this.password
    )

    companion object {
        fun fromDto(dto: UserDto) = User(
            id = dto.id,
            name = dto.name,
            email = dto.email,
            password = dto.password
        )
    }
}
