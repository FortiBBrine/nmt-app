package me.fortibrine.nmtapp.dto.login

data class LoginResponseDto(
    val token: String? = null,
    val errors: Map<String, String?> = mapOf()
)
