package me.fortibrine.nmtapp.service

import me.fortibrine.nmtapp.model.User
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.security.oauth2.jwt.*
import org.springframework.stereotype.Service
import java.time.Instant
import java.time.temporal.ChronoUnit

@Service
class TokenService (
    @Qualifier("accessJwtEncoder") private val accessJwtEncoder: JwtEncoder,
    @Qualifier("accessJwtDecoder") private val accessJwtDecoder: JwtDecoder,
    @Qualifier("refreshJwtEncoder") private val refreshJwtEncoder: JwtEncoder,
    @Qualifier("refreshJwtDecoder") private val refreshJwtDecoder: JwtDecoder,

    private val userService: UserService
) {

    fun createAccessToken(user: User): String {
        val jwsHeader = JwsHeader.with { "HS256" }.build()
        val claims = JwtClaimsSet.builder()
            .issuedAt(Instant.now())
            .expiresAt(Instant.now().plus(15L, ChronoUnit.MINUTES))
            .subject(user.username)
            .claim("userId", user.id)
            .build()
        return accessJwtEncoder.encode(JwtEncoderParameters.from(jwsHeader, claims)).tokenValue
    }

    fun parseAccessToken(token: String): User? {
        return try {
            val jwt = accessJwtDecoder.decode(token)
            val userId = jwt.claims["userId"] as Long
            userService.findById(userId)
        } catch (e: Exception) {
            null
        }
    }

    fun createRefreshToken(user: User): String {
        val jwsHeader = JwsHeader.with { "HS256" }.build()
        val claims = JwtClaimsSet.builder()
            .issuedAt(Instant.now())
            .expiresAt(Instant.now().plus(7L, ChronoUnit.DAYS))
            .subject(user.username)
            .claim("userId", user.id)
            .build()
        return refreshJwtEncoder.encode(JwtEncoderParameters.from(jwsHeader, claims)).tokenValue
    }

    fun parseRefreshToken(token: String): User? {
        return try {
            val jwt = refreshJwtDecoder.decode(token)
            val userId = jwt.claims["userId"] as Long
            userService.findById(userId)
        } catch (e: Exception) {
            null
        }
    }

}