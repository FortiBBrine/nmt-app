package me.fortibrine.nmtapp.dto.study.response

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "Відповідь на створення предмета")
data class StudyResponseDto (
    @field:Schema(description = "Ідентифікатор")
    val id: Long,

    @field:Schema(description = "Ім'я")
    val name: String,
)