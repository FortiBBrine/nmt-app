package me.fortibrine.nmtapp.dto.question.request

data class QuestionRequestDto (
    val description: String,
    val answers: List<String>,
    val trueAnswers: List<Int>,
    val type: String,
)
