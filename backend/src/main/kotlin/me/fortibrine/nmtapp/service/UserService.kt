package me.fortibrine.nmtapp.service

import me.fortibrine.nmtapp.model.User
import me.fortibrine.nmtapp.repository.UserRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class UserService (
    private val userRepository: UserRepository
) {

    fun findById(id: Long): User? = userRepository.findByIdOrNull(id)
    fun findByUsername(username: String): User? = userRepository.findByUsername(username)
    fun existsByName(name: String): Boolean = userRepository.existsByUsername(name)
    fun save(user: User): User = userRepository.save(user)

}