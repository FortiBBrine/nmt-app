package me.fortibrine.nmtapp.config

import com.nimbusds.jose.jwk.source.ImmutableSecret
import com.nimbusds.jose.proc.SecurityContext
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.security.oauth2.jwt.JwtDecoder
import org.springframework.security.oauth2.jwt.JwtEncoder
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder
import javax.crypto.spec.SecretKeySpec

@Configuration
class JwtEncodingConfig (
    @Value("\${security.jwt.access-key}")
    private val accessKey: String,

    @Value("\${security.jwt.refresh-key}")
    private val refreshKey: String,
) {

    private val accessKeySpec = SecretKeySpec(accessKey.toByteArray(), "HmacSHA256")
    private val refreshKeySpec = SecretKeySpec(refreshKey.toByteArray(), "HmacSHA256")

    @Bean
    @Qualifier("accessJwtDecoder")
    @Primary
    fun accessJwtDecoder(): JwtDecoder = NimbusJwtDecoder.withSecretKey(accessKeySpec).build()

    @Bean
    @Qualifier("accessJwtEncoder")
    @Primary
    fun accessJwtEncoder(): JwtEncoder {
        val secret = ImmutableSecret<SecurityContext>(accessKeySpec)
        return NimbusJwtEncoder(secret)
    }

    @Bean
    @Qualifier("refreshJwtDecoder")
    fun refreshJwtDecoder(): JwtDecoder = NimbusJwtDecoder.withSecretKey(refreshKeySpec).build()

    @Bean
    @Qualifier("refreshJwtEncoder")
    fun refreshJwtEncoder(): JwtEncoder {
        val secret = ImmutableSecret<SecurityContext>(refreshKeySpec)
        return NimbusJwtEncoder(secret)
    }

}