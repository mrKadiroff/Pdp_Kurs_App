package com.example.pdp_dastur.models

import java.io.Serializable

class Mentor:Serializable {
    var id: Int? = null
    var mentor_surname: String? = null
    var mentor_name: String? = null
    var mentor_otch: String? = null
    var mentor_kurs_id : Kurs? = null

    constructor()
    constructor(
        mentor_surname: String?,
        mentor_name: String?,
        mentor_otch: String?,
        mentor_kurs_id: Kurs?
    ) {
        this.mentor_surname = mentor_surname
        this.mentor_name = mentor_name
        this.mentor_otch = mentor_otch
        this.mentor_kurs_id = mentor_kurs_id
    }

    constructor(
        id: Int?,
        mentor_surname: String?,
        mentor_name: String?,
        mentor_otch: String?,
        mentor_kurs_id: Kurs?
    ) {
        this.id = id
        this.mentor_surname = mentor_surname
        this.mentor_name = mentor_name
        this.mentor_otch = mentor_otch
        this.mentor_kurs_id = mentor_kurs_id
    }

    constructor(
        mentor_surname: String,
        mentor_name: String,
        mentor_otch: String,
        mentor_kurs_id: Int?
    )


}