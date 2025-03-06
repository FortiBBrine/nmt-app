package me.fortibrine.nmtapp.dto.register

data class RegisterResponseDto(
    val result: Map<String, String?>,
    val token: String?
)
