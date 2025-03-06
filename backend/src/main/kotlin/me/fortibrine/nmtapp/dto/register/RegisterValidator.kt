package me.fortibrine.nmtapp.dto.register

import me.fortibrine.nmtapp.model.User
import me.fortibrine.nmtapp.service.UserService
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.validation.Errors
import org.springframework.validation.Validator

@Component
class RegisterValidator(
    private val userService: UserService
): Validator {
    override fun supports(clazz: Class<*>) = RegisterRequestDto::class.java == clazz

    override fun validate(target: Any, errors: Errors) {
        val payload = target as RegisterRequestDto

        if (SecurityContextHolder.getContext().authentication.principal is User) {
            errors.rejectValue("username", "", "You are already authenticated")
            return
        }

        if (userService.existsByName(payload.username.replace(" ", ""))) {
            errors.rejectValue("username", "", "Already exists")
        }

    }

}