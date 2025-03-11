package me.fortibrine.nmtapp.repository

import me.fortibrine.nmtapp.model.Question
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface QuestionRepository: JpaRepository<Question, Long> {
    fun findAllByStudyId(studyId: Long): List<Question>
    fun findAllByStudy_IdAndType(id: Long, type: String): List<Question>

    @Query("SELECT DISTINCT q.type FROM nmt_questions q WHERE q.study.id = :studyId")
    fun findAllTypesByStudyId(@Param("studyId") studyId: Long): Set<String>
}