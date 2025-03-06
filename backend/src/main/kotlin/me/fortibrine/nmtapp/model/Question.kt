package me.fortibrine.nmtapp.model

import jakarta.persistence.*

@Entity(name = "nms-questions")
class Question (

    @field:Id
    @field:GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null,

    @field:ManyToOne
    var study: Study? = null,
    var description: String = "",

    @field:ElementCollection(fetch = FetchType.EAGER)
    var answers: List<String> = listOf(),

    @field:ElementCollection(fetch = FetchType.EAGER)
    var trueAnswers: List<String> = listOf(),

    var type: String = "",

)