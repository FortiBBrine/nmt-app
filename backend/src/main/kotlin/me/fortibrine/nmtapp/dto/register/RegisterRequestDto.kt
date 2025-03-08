package me.fortibrine.nmtapp.dto.register

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size

data class RegisterRequestDto (
    @field:NotBlank(message = "Будь ласка, вкажіть електронну пошту")
    @field:Email(message = "Будь ласка, вкажіть коректну електронну адресу")
    val email: String,

    @field:NotBlank(message = "Будь ласка, вкажіть ім'я")
    val name: String,

    @field:NotBlank(message = "Будь ласка, вкажіть ім'я користувача")
    @field:Pattern(regexp = "^[a-zA-Z0-9]+$", message = "Ім'я користувача може містити лише літери англійського алфавіту та цифри")
    val username: String,

    @field:Size(min = 8, max = 32, message = "Пароль має містити щонайменше 8 символів")
    val password: String,
)