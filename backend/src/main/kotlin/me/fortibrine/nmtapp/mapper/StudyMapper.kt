package me.fortibrine.nmtapp.mapper

import me.fortibrine.nmtapp.dto.study.request.StudyRequestDto
import me.fortibrine.nmtapp.dto.study.response.StudyDto
import me.fortibrine.nmtapp.model.Study
import org.mapstruct.Mapper
import org.mapstruct.Mapping

@Mapper(componentModel = "spring")
interface StudyMapper {

    fun toDto(input: Study): StudyDto

    @Mapping(target = "id", ignore = true)
    fun toEntity(dto: StudyRequestDto): Study

}