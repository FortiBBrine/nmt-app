package me.fortibrine.nmtapp.dto.login

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern

data class LoginRequestDto (
    @field:NotBlank(message = "Будь ласка, вкажіть ім'я користувача")
    @field:Pattern(regexp = "^[a-zA-Z0-9]+$", message = "Ім'я користувача може містити лише літери англійського алфавіту та цифри")
    val username: String,
    val password: String,
)
