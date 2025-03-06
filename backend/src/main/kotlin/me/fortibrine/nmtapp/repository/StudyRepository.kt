package me.fortibrine.nmtapp.repository

import me.fortibrine.nmtapp.model.Study
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface StudyRepository: JpaRepository<Study, Long> {
}
