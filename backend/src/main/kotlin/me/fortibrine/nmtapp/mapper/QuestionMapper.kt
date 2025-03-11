package me.fortibrine.nmtapp.mapper

import me.fortibrine.nmtapp.dto.question.request.QuestionRequestDto
import me.fortibrine.nmtapp.dto.question.response.QuestionDto
import me.fortibrine.nmtapp.model.Question
import me.fortibrine.nmtapp.model.Study
import org.mapstruct.Mapper
import org.mapstruct.Mapping

@Mapper(componentModel = "spring")
interface QuestionMapper {
    @Mapping(source = "study.name", target = "study")
    fun toDto(question: Question): QuestionDto

    @Mapping(source = "study", target = "study")
    fun toEntity(dto: QuestionRequestDto, study: Study): Question
}