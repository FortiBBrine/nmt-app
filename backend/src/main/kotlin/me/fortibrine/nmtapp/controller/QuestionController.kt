package me.fortibrine.nmtapp.controller

import me.fortibrine.nmtapp.dto.question.response.QuestionDto
import me.fortibrine.nmtapp.repository.QuestionRepository
import me.fortibrine.nmtapp.repository.StudyRepository
import org.springframework.data.domain.PageRequest
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/questions")
class QuestionController(
    private val questionRepository: QuestionRepository,
    private val studyRepository: StudyRepository,
) {

    @GetMapping
    fun allQuestions(
        @RequestParam(required = false) limit: Int?,
        @RequestParam(required = false) page: Int?,
    ): List<QuestionDto> {

        if (limit == null) {
            return questionRepository.findAll()
                .filter { question -> question.id != null }
                .filter { question -> question.study != null }
                .map { question -> QuestionDto(
                    id = question.id!!,
                    study = question.study!!.name,
                    description = question.description,
                    answers = question.answers.toList(),
                    trueAnswers = question.trueAnswers.toList(),
                    type = question.type
                ) }
        }

        return questionRepository.findAll(
            PageRequest.of(page ?: 0, limit)
        )
            .toList()
            .filter { question -> question.id != null }
            .filter { question -> question.study != null }
            .map { question -> QuestionDto(
                id = question.id!!,
                study = question.study!!.name,
                description = question.description,
                answers = question.answers.toList(),
                trueAnswers = question.trueAnswers.toList(),
                type = question.type
            ) }
    }

}
