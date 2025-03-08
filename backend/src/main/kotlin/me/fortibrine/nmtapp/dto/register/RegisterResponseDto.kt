package me.fortibrine.nmtapp.dto.register

data class RegisterResponseDto(
    val token: String? = null,
    val errors: Map<String, String?> = mapOf()
)
