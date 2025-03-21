package me.fortibrine.nmtapp.dto.question.request

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "Запит на створення питання")
data class QuestionRequestDto (
    @field:Schema(description = "Опис завдання")
    val description: String,

    @field:Schema(description = "Список відповідей")
    val answers: List<String>,

    @field:Schema(description = "Правильні відповіді")
    val trueAnswers: List<Int>,

    @field:Schema(description = "Тип завдання")
    val type: String,
)
