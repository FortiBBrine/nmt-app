package me.fortibrine.nmtapp.dto.register

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "Відповідь на реєстрацію")
data class RegisterResponseDto(
    @field:Schema(description = "Токен JWT")
    val token: String? = null,

    @field:Schema(description = "Результат валідації")
    val errors: Map<String, String?> = mapOf()
)
