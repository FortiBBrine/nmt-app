package me.fortibrine.nmtapp.dto.study.request

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "Запит на створення предмета")
data class StudyRequestDto (
    @field:Schema(description = "Ім'я")
    val name: String
)
