package me.fortibrine.nmtapp.dto.login

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern

@Schema(description = "Запит на автентифікацію")
data class LoginRequestDto (
    @field:Schema(description = "Користувач")
    @field:NotBlank(message = "Будь ласка, вкажіть ім'я користувача")
    @field:Pattern(regexp = "^[a-zA-Z0-9]+$", message = "Ім'я користувача може містити лише літери англійського алфавіту та цифри")
    val username: String,

    @field:Schema(description = "Пароль")
    val password: String,
)
