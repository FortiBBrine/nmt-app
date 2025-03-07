package me.fortibrine.nmtapp.controller

import jakarta.servlet.http.Cookie
import jakarta.servlet.http.HttpServletResponse
import jakarta.validation.Valid
import me.fortibrine.nmtapp.dto.login.LoginRequestDto
import me.fortibrine.nmtapp.dto.login.LoginResponseDto
import me.fortibrine.nmtapp.dto.login.LoginStatusDto
import me.fortibrine.nmtapp.dto.login.LoginValidator
import me.fortibrine.nmtapp.dto.register.RegisterRequestDto
import me.fortibrine.nmtapp.dto.register.RegisterResponseDto
import me.fortibrine.nmtapp.dto.register.RegisterValidator
import me.fortibrine.nmtapp.model.User
import me.fortibrine.nmtapp.service.HashService
import me.fortibrine.nmtapp.service.TokenService
import me.fortibrine.nmtapp.service.UserService
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.context.SecurityContextHolder
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
    private val registerValidator: RegisterValidator,

    @Value("\${security.jwt.expiration-time}")
    private val expirationTime: Int,
) {

    @PostMapping("/login")
    fun login(
        @Valid
        @RequestBody
        payload: LoginRequestDto,

        bindingResult: BindingResult,
        response: HttpServletResponse
    ): RegisterResponseDto {

        loginValidator.validate(payload, bindingResult)

        if (bindingResult.hasErrors()) {
            return RegisterResponseDto(
                result = mapOf(
                    *bindingResult.
                    allErrors
                        .map { (it as FieldError).field to it.defaultMessage }
                        .toTypedArray()
                )
            )
        }

        val user = userService.findByUsername(payload.username) as User
        val token = tokenService.createToken(user)

        val cookie = Cookie("accessToken", token).apply {
            isHttpOnly = true
            path = "/"
            maxAge = expirationTime
        }

        response.addCookie(cookie)

        return RegisterResponseDto(
            result = mapOf()
        )
    }

    @PostMapping("/register")
    fun register(
        @Valid
        @RequestBody
        payload: RegisterRequestDto,

        bindingResult: BindingResult,
        response: HttpServletResponse
    ): LoginResponseDto {

        registerValidator.validate(payload, bindingResult)

        if (bindingResult.hasErrors()) {
            return LoginResponseDto(
                result = mapOf(
                    *bindingResult.
                    allErrors
                        .map { (it as FieldError).field to it.defaultMessage }
                        .toTypedArray()
                )
            )
        }

        val user = User(
            email = payload.email,
            name = payload.name,
            username = payload.username,
            password = hashService.hashBcrypt(payload.password),
        )

        val savedUser = userService.save(user)
        val token = tokenService.createToken(savedUser)

        val cookie = Cookie("accessToken", token).apply {
            isHttpOnly = true
            path = "/"
            maxAge = expirationTime
        }

        response.addCookie(cookie)

        return LoginResponseDto(
            result = mapOf()
        )
    }

    @PostMapping("/logout")
    fun logout(
        response: HttpServletResponse
    ) {
        val cookie = Cookie("accessToken", null).apply {
            isHttpOnly = true
            path = "/"
            maxAge = expirationTime
        }

        response.addCookie(cookie)
    }

    @GetMapping("/status")
    fun status(): LoginStatusDto {
        return LoginStatusDto(
            SecurityContextHolder.getContext().authentication?.principal is User
        )
    }

}