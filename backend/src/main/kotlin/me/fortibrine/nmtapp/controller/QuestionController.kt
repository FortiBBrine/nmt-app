package me.fortibrine.nmtapp.controller

import me.fortibrine.nmtapp.dto.question.response.QuestionDto
import me.fortibrine.nmtapp.mapper.QuestionMapper
import me.fortibrine.nmtapp.repository.QuestionRepository
import org.springframework.data.domain.PageRequest
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/questions")
class QuestionController(
    private val questionRepository: QuestionRepository,

    private val questionMapper: QuestionMapper
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
                .map { questionMapper.toDto(it) }
        }

        return questionRepository.findAll(
            PageRequest.of(page ?: 0, limit)
        )
            .toList()
            .filter { question -> question.id != null }
            .filter { question -> question.study != null }
            .map { questionMapper.toDto(it) }
    }

}
