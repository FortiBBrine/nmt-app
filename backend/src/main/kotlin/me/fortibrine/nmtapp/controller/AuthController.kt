package me.fortibrine.nmtapp.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import me.fortibrine.nmtapp.dto.login.LoginRequestDto
import me.fortibrine.nmtapp.dto.login.LoginResponseDto
import me.fortibrine.nmtapp.dto.login.LoginValidator
import me.fortibrine.nmtapp.dto.refresh.RefreshRequestDto
import me.fortibrine.nmtapp.dto.refresh.RefreshResponseDto
import me.fortibrine.nmtapp.dto.register.RegisterRequestDto
import me.fortibrine.nmtapp.dto.register.RegisterResponseDto
import me.fortibrine.nmtapp.dto.register.RegisterValidator
import me.fortibrine.nmtapp.model.User
import me.fortibrine.nmtapp.service.HashService
import me.fortibrine.nmtapp.service.TokenService
import me.fortibrine.nmtapp.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Auth", description = "REST API для автентифікації та реєстрації")
class AuthController (
    private val hashService: HashService,
    private val tokenService: TokenService,
    private val userService: UserService,
    private val loginValidator: LoginValidator,
    private val registerValidator: RegisterValidator,
) {

    @PostMapping("/login")
    @Operation(summary = "Увійти в акаунт")
    @ApiResponse(responseCode = "200", description = "Успішний вхід")
    @ApiResponse(responseCode = "401", description = "Невірний логін або пароль")
    fun login(
        @Valid
        @RequestBody
        payload: LoginRequestDto,

        bindingResult: BindingResult,
    ): ResponseEntity<LoginResponseDto> {

        loginValidator.validate(payload, bindingResult)

        if (bindingResult.hasErrors()) {
            return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(LoginResponseDto(
                    errors = bindingResult.fieldErrors.associate {
                        it.field to it.defaultMessage.orEmpty()
                    }
                ))
        }

        val user = userService.findByUsername(payload.username) as User

        return ResponseEntity.ok(LoginResponseDto(
            accessToken = tokenService.createAccessToken(user),
            refreshToken = tokenService.createRefreshToken(user)
        ))
    }

    @PostMapping("/register")
    @Operation(summary = "Зареєструвати акаунт")
    @ApiResponse(responseCode = "200", description = "Успішна реєстрація")
    @ApiResponse(responseCode = "400", description = "Помилка валідації")
    fun register(
        @Valid
        @RequestBody
        payload: RegisterRequestDto,

        bindingResult: BindingResult,
    ): ResponseEntity<RegisterResponseDto> {

        registerValidator.validate(payload, bindingResult)

        if (bindingResult.hasErrors()) {
            return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(
                    RegisterResponseDto(
                    errors = bindingResult.fieldErrors.associate {
                        it.field to it.defaultMessage.orEmpty()
                    }
                ))
        }

        val user = User(
            email = payload.email,
            name = payload.name,
            username = payload.username,
            password = hashService.hashBcrypt(payload.password),
        )

        val savedUser = userService.save(user)

        return ResponseEntity.ok(RegisterResponseDto(
            accessToken = tokenService.createAccessToken(savedUser),
            refreshToken = tokenService.createRefreshToken(savedUser)
        ))
    }

    @PostMapping("/refresh")
    fun refresh(
        @RequestBody payload: RefreshRequestDto
    ): ResponseEntity<Any> {
        val user = tokenService.parseRefreshToken(payload.refreshToken)
            ?: return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .build()

        return ResponseEntity.ok(RefreshResponseDto(
            accessToken = tokenService.createAccessToken(user),
        ))
    }

}