package me.fortibrine.nmtapp.dto.login

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "Відповідь на авторизацію")
data class LoginResponseDto(
    @field:Schema(description = "Токен JWT")
    val token: String? = null,

    @field:Schema(description = "Результат валідації")
    val errors: Map<String, String?> = mapOf()
)
