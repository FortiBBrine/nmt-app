package me.fortibrine.nmtapp.controller

import me.fortibrine.nmtapp.dto.question.request.QuestionRequestDto
import me.fortibrine.nmtapp.dto.question.response.QuestionDto
import me.fortibrine.nmtapp.dto.study.request.StudyRequestDto
import me.fortibrine.nmtapp.dto.study.response.StudyDto
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
class StudyController (
    private val studyRepository: StudyRepository,
    private val questionRepository: QuestionRepository,

    private val questionMapper: QuestionMapper,
    private val studyMapper: StudyMapper
) {

    @GetMapping
    fun allStudies(): List<StudyDto> {
        return studyRepository.findAll()
            .filter { study -> study.id != null }
            .map { studyMapper.toDto(it) }
    }

    @GetMapping("/{id}/questions")
    fun allQuestionsOfStudy(
        @PathVariable id: Long,
    ): List<QuestionDto> {
        return questionRepository.findAllByStudyId(id)
            .filter { question -> question.id != null }
            .filter { question -> question.study != null }
            .map { question -> questionMapper.toDto(question) }
    }

    @GetMapping("/{id}/types")
    fun allQuestionTypes(
        @PathVariable id: Long,
    ): Set<String> {
        return questionRepository.findAllTypesByStudyId(id)
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/{id}/add")
    fun addQuestion(
        @PathVariable id: Long,

        @RequestBody
        questionDto: QuestionRequestDto
    ): QuestionDto {
        val study = studyRepository.findByIdOrNull(id)
        val question = questionRepository.save(questionMapper.toEntity(questionDto, study!!))

        return questionMapper.toDto(question)
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    fun addStudy(
        @RequestBody
        studyDto: StudyRequestDto,
    ): StudyDto {
        val study = studyRepository.save(studyMapper.toEntity(studyDto))
        return studyMapper.toDto(study)
    }

    @GetMapping("/{id}/generate")
    fun generate(
        @PathVariable id: Long,

        @RequestParam payload: Map<String, String>,
    ): List<QuestionDto> {
        val questions = mutableListOf<QuestionDto>()

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
