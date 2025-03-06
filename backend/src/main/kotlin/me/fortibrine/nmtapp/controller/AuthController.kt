package me.fortibrine.nmtapp.controller

import jakarta.validation.Valid
import me.fortibrine.nmtapp.dto.login.LoginRequestDto
import me.fortibrine.nmtapp.dto.login.LoginResponseDto
import me.fortibrine.nmtapp.dto.login.LoginValidator
import me.fortibrine.nmtapp.dto.register.RegisterRequestDto
import me.fortibrine.nmtapp.dto.register.RegisterResponseDto
import me.fortibrine.nmtapp.dto.register.RegisterValidator
import me.fortibrine.nmtapp.model.User
import me.fortibrine.nmtapp.service.HashService
import me.fortibrine.nmtapp.service.TokenService
import me.fortibrine.nmtapp.service.UserService
import org.springframework.validation.BindingResult
import org.springframework.validation.FieldError
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/auth")
class AuthController (
    private val hashService: HashService,
    private val tokenService: TokenService,
    private val userService: UserService,
    private val loginValidator: LoginValidator,
    private val registerValidator: RegisterValidator
) {

    @PostMapping("/login")
    fun login(
        @Valid
        @RequestBody
        payload: LoginRequestDto,

        bindingResult: BindingResult
    ): RegisterResponseDto {

        loginValidator.validate(payload, bindingResult)

        if (bindingResult.hasErrors()) {
            return RegisterResponseDto(
                result = mapOf(
                    *bindingResult.
                    allErrors
                        .map { (it as FieldError).field to it.defaultMessage }
                        .toTypedArray()
                ),
                token = null,
            )
        }

        val user = userService.findByUsername(payload.username) as User

        return RegisterResponseDto(
            result = mapOf(),
            token = tokenService.createToken(user),
        )
    }

    @PostMapping("/register")
    fun register(
        @Valid
        @RequestBody
        payload: RegisterRequestDto,

        bindingResult: BindingResult
    ): LoginResponseDto {

        registerValidator.validate(payload, bindingResult)

        if (bindingResult.hasErrors()) {
            return LoginResponseDto(
                result = mapOf(
                    *bindingResult.
                    allErrors
                        .map { (it as FieldError).field to it.defaultMessage }
                        .toTypedArray()
                ),
                token = null,
            )
        }

        val user = User(
            email = payload.email,
            name = payload.name,
            username = payload.username,
            password = hashService.hashBcrypt(payload.password),
        )

        val savedUser = userService.save(user)

        return LoginResponseDto(
            result = mapOf(),
            token = tokenService.createToken(savedUser),
        )
    }

}