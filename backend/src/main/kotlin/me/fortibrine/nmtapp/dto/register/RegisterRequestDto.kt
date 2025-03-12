package me.fortibrine.nmtapp.dto.register

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size

@Schema(description = "Запит на реєстрацію")
data class RegisterRequestDto (
    @field:Schema(description = "Електронна пошта")
    @field:NotBlank(message = "Будь ласка, вкажіть електронну пошту")
    @field:Email(message = "Будь ласка, вкажіть коректну електронну адресу")
    val email: String,

    @field:Schema(description = "Ім'я користувача")
    @field:NotBlank(message = "Будь ласка, вкажіть ім'я")
    @field:Size(min = 6, max = 32, message = "Довжина має бути від 6 до 32 символів")
    val name: String,

    @field:Schema(description = "Користувач")
    @field:NotBlank(message = "Будь ласка, вкажіть ім'я користувача")
    @field:Size(min = 6, max = 20, message = "Довжина має бути від 6 до 20 символів")
    @field:Pattern(regexp = "^[a-zA-Z0-9]+$", message = "Ім'я користувача може містити лише літери англійського алфавіту та цифри")
    val username: String,

    @field:Schema(description = "Пароль")
    @field:Size(min = 8, max = 32, message = "Пароль має містити щонайменше 8 символів")
    val password: String,
)