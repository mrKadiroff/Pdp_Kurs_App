package com.example.pdp_dastur.models

import java.io.Serializable

class Guruh:Serializable {
    var id: Int? = null
    var gr_name: String? = null
    var gr_kurs_id: Kurs? = null
    var gr_mentor_id: Mentor? = null
    var gr_time : String? = null
    var gr_days : String? = null
    var gr_open : String? = null


    constructor()
    constructor(
        gr_name: String?,
        gr_kurs_id: Kurs?,
        gr_mentor_id: Mentor?,
        gr_time: String?,
        gr_days: String?,
        gr_open : String?
    ) {
        this.gr_name = gr_name
        this.gr_kurs_id = gr_kurs_id
        this.gr_mentor_id = gr_mentor_id
        this.gr_time = gr_time
        this.gr_days = gr_days
        this.gr_open = gr_open
    }

    constructor(
        id: Int?,
        gr_name: String?,
        gr_kurs_id: Kurs?,
        gr_mentor_id: Mentor?,
        gr_time: String?,
        gr_days: String?,
        gr_open : String?
    ) {
        this.id = id
        this.gr_name = gr_name
        this.gr_kurs_id = gr_kurs_id
        this.gr_mentor_id = gr_mentor_id
        this.gr_time = gr_time
        this.gr_days = gr_days
        this.gr_open = gr_open
    }


}