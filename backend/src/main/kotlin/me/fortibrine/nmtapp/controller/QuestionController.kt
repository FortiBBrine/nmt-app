package me.fortibrine.nmtapp.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import me.fortibrine.nmtapp.dto.question.response.QuestionResponseDto
import me.fortibrine.nmtapp.mapper.QuestionMapper
import me.fortibrine.nmtapp.repository.QuestionRepository
import org.springframework.data.domain.PageRequest
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/questions")
@Tag(name = "Questions", description = "REST API для керування питаннями")
class QuestionController(
    private val questionRepository: QuestionRepository,

    private val questionMapper: QuestionMapper
) {

    @GetMapping
    @Operation(summary = "Отримати список запитань")
    @ApiResponse(responseCode = "200", description = "Отримано список запитань")
    fun allQuestions(
        @Parameter(name = "limit", description = "Кількість запитань на одній сторонці")
        @RequestParam(required = false, defaultValue = "10")
        limit: Int,

        @Parameter(name = "page", description = "Номер сторінки")
        @RequestParam(required = false, defaultValue = "1")
        page: Int,
    ): List<QuestionResponseDto> {
        return questionRepository.findAll(
            PageRequest.of(page, limit)
        )
            .toList()
            .filter { question -> question.id != null }
            .filter { question -> question.study != null }
            .map { questionMapper.toDto(it) }
    }

}
