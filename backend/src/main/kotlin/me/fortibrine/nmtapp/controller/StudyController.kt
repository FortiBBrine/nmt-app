package me.fortibrine.nmtapp.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import me.fortibrine.nmtapp.dto.question.request.QuestionRequestDto
import me.fortibrine.nmtapp.dto.question.response.QuestionResponseDto
import me.fortibrine.nmtapp.dto.study.request.StudyRequestDto
import me.fortibrine.nmtapp.dto.study.response.StudyResponseDto
import me.fortibrine.nmtapp.mapper.QuestionMapper
import me.fortibrine.nmtapp.mapper.StudyMapper
import me.fortibrine.nmtapp.repository.QuestionRepository
import me.fortibrine.nmtapp.repository.StudyRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/studies")
@Tag(name = "Studies", description = "REST API для керування предметами")
class StudyController (
    private val studyRepository: StudyRepository,
    private val questionRepository: QuestionRepository,

    private val questionMapper: QuestionMapper,
    private val studyMapper: StudyMapper
) {

    @GetMapping
    @Operation(summary = "Отримати список предметів")
    @ApiResponse(responseCode = "200", description = "Отримано список предметів")
    fun allStudies(): List<StudyResponseDto> {
        return studyRepository.findAll()
            .filter { study -> study.id != null }
            .map { studyMapper.toDto(it) }
    }

    @GetMapping("/{id}/questions")
    @Operation(
        summary = "Отримати всі питання навчального курсу",
        description = "Повертає список запитань для конкретного навчального курсу за його ID"
    )
    @ApiResponse(responseCode = "200", description = "Успішне отримання списку питань")
    @ApiResponse(responseCode = "404", description = "Навчальний курс не знайдено")
    fun allQuestionsOfStudy(
        @PathVariable id: Long,
    ): List<QuestionResponseDto> {
        return questionRepository.findAllByStudyId(id)
            .filter { question -> question.id != null }
            .filter { question -> question.study != null }
            .map { question -> questionMapper.toDto(question) }
    }

    @GetMapping("/{id}/types")
    @Operation(
        summary = "Отримати всі типи питань для навчального курсу",
        description = "Повертає унікальний список типів питань, пов'язаних із курсом за його ID"
    )
    @ApiResponse(responseCode = "200", description = "Успішне отримання списку типів")
    @ApiResponse(responseCode = "404", description = "Навчальний курс не знайдено")
    fun allQuestionTypes(
        @PathVariable
        @Parameter(description = "ID навчального курсу", example = "1")
        id: Long,
    ): Set<String> {
        return questionRepository.findAllTypesByStudyId(id)
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/{id}/add")
    @Operation(
        summary = "Додати питання до курсу",
        description = "Додає нове питання до вказаного навчального курсу (лише для адміністраторів)"
    )
    @ApiResponse(responseCode = "201", description = "Питання успішно додано")
    @ApiResponse(responseCode = "400", description = "Некоректний запит")
    @ApiResponse(responseCode = "403", description = "Недостатньо прав доступу")
    fun addQuestion(
        @PathVariable
        @Parameter(description = "ID навчального курсу")
        id: Long,

        @RequestBody
        questionDto: QuestionRequestDto
    ): QuestionResponseDto {
        val study = studyRepository.findByIdOrNull(id)
        val question = questionRepository.save(questionMapper.toEntity(questionDto, study!!))

        return questionMapper.toDto(question)
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    @Operation(
        summary = "Додати новий навчальний курс",
        description = "Створює новий навчальний курс (лише для адміністраторів)"
    )
    @ApiResponse(responseCode = "201", description = "Курс успішно створено")
    @ApiResponse(responseCode = "400", description = "Некоректні вхідні дані")
    @ApiResponse(responseCode = "403", description = "Недостатньо прав доступу")
    fun addStudy(
        @RequestBody
        studyDto: StudyRequestDto,
    ): StudyResponseDto {
        val study = studyRepository.save(studyMapper.toEntity(studyDto))
        return studyMapper.toDto(study)
    }

    @GetMapping("/{id}/generate")
    @Operation(
        summary = "Генерувати випадкові питання за типами",
        description = "Генерує список питань на основі переданих параметрів типу та кількості питань"
    )
    @ApiResponse(responseCode = "200", description = "Список питань успішно згенеровано")
    @ApiResponse(responseCode = "400", description = "Некоректні параметри")
    @ApiResponse(responseCode = "404", description = "Навчальний курс не знайдено")
    fun generate(
        @PathVariable
        @Parameter(description = "ID навчального курсу")
        id: Long,

        @RequestParam payload: Map<String, String>,
    ): List<QuestionResponseDto> {
        val questions = mutableListOf<QuestionResponseDto>()

        val questionCountMap = payload.map { entry ->
            entry.key to entry.value.toInt()
        }.toMap()

        questionCountMap.forEach { (type, count) ->
            val questionsOfType = questionRepository.findAllByStudy_IdAndType(id, type)

            val selectedQuestions = questionsOfType.shuffled().take(count)

            questions.addAll(selectedQuestions
                .filter { question -> question.id != null }
                .filter { question -> question.study != null }
                .map { questionMapper.toDto(it) })
        }

        return questions
    }

}
