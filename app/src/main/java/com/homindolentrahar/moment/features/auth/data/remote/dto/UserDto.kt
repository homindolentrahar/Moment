package com.homindolentrahar.moment.features.auth.data.remote.dto

data class UserDto(
    val id: String,
    val name: String,
    val bio: String,
    val email: String,
    val phone: String,
    val avatarUrl: String,
    val createdAt: Long,
    val updatedAt: Long
) {
    companion object {
        const val COLLECTION = "users"

        fun fromDocumentSnapshot(data: Map<String, Any>): UserDto {
            return UserDto(
                id = data["id"] as String,
                name = data["name"] as String,
                bio = data["bio"] as String,
                email = data["email"] as String,
                phone = data["phone"] as String,
                avatarUrl = data["avatar_url"] as String,
                createdAt = data["created_at"] as Long,
                updatedAt = data["updated_at"] as Long
            )
        }

        fun fromAuthUserDto(
            authUserDto: AuthUserDto,
            name: String? = null,
            updatedAt: Long? = null
        ): UserDto {
            return UserDto(
                id = authUserDto.uid,
                name = name ?: authUserDto.name ?: "No Name",
                bio = "No Bio Available",
                email = authUserDto.email ?: "",
                phone = authUserDto.phone ?: "",
                avatarUrl = "",
                createdAt = authUserDto.createdAt,
                updatedAt = updatedAt ?: authUserDto.createdAt
            )
        }
    }
}
