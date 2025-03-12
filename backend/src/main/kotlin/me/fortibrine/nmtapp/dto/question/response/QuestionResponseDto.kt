package me.fortibrine.nmtapp.dto.question.response

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "Запитання")
data class QuestionResponseDto (
    @field:Schema(description = "Ідентифікатор завдання")
    val id: Long,

    @field:Schema(description = "Предмет")
    val study: String,

    @field:Schema(description = "Опис завдання")
    val description: String,

    @field:Schema(description = "Список відповідей")
    val answers: List<String>,

    @field:Schema(description = "Правильні відповіді")
    val trueAnswers: List<Int>,

    @field:Schema(description = "Тип завдання")
    val type: String,
)