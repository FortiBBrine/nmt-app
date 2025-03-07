package me.fortibrine.nmtapp.config

import jakarta.servlet.http.HttpServletRequest
import me.fortibrine.nmtapp.service.TokenService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

@Configuration
@EnableWebSecurity
class SecurityConfig (
    private val tokenService: TokenService,
) {

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {

        http.addFilterBefore({ request, response, chain ->
            val httpRequest = request as HttpServletRequest

            val accessToken = request.cookies?.find { it.name == "accessToken" }?.value
            if (accessToken != null) {
                val user = tokenService.parseToken(accessToken)
                if (user == null) {
                    SecurityContextHolder.clearContext()
                    chain.doFilter(request, response)
                    return@addFilterBefore
                }

                val authentication = UsernamePasswordAuthenticationToken(user, null, user.roles.map { SimpleGrantedAuthority(it) })
                SecurityContextHolder.getContext().authentication = authentication
            }

            chain.doFilter(request, response)
        }, UsernamePasswordAuthenticationFilter::class.java)

        // Define public and private routes
        http.authorizeHttpRequests { request ->
            request
                .requestMatchers("/api/auth/**").permitAll()
                .requestMatchers("/api/test").hasRole("ADMIN")
                .requestMatchers("/api/**").authenticated()
                .anyRequest().permitAll()
        }

        // Configure JWT
        http.oauth2ResourceServer { oauth2 ->
            oauth2.jwt(Customizer.withDefaults())
        }

        // Other configuration
        http.cors(Customizer.withDefaults())
        http.sessionManagement { httpSession ->
            httpSession.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        }
        http.csrf {
            it.disable()
        }
        http.headers { headers ->
            headers.frameOptions { it.disable() }
            headers.xssProtection { it.disable() }
        }

        return http.build()
    }

    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource {
        val configuration = CorsConfiguration()

        configuration.allowedOrigins = listOf(
            "http://localhost:3000",  // Dev
            "http://0.0.0.0:3000",
            "http://107.161.154.205:3000" // Production
        )

        configuration.allowedMethods = listOf("GET", "POST", "PUT", "DELETE", "OPTIONS")
        configuration.allowedHeaders = listOf("Authorization", "Content-Type")
        configuration.allowCredentials = true

        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", configuration)
        return source
    }

}