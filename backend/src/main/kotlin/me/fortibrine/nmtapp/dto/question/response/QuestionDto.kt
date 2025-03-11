package me.fortibrine.nmtapp.dto.question.response

data class QuestionDto (
    val id: Long,
    val study: String,
    val description: String,
    val answers: List<String>,
    val trueAnswers: List<Int>,
    val type: String,
)