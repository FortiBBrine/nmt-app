package me.fortibrine.nmtapp.utils

import me.fortibrine.nmtapp.model.User
import org.springframework.security.core.Authentication

fun Authentication.toUser() = principal as User
