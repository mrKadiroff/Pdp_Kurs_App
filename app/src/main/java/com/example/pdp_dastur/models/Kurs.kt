package com.example.pdp_dastur.models

import java.io.Serializable

class Kurs:Serializable {
    var id: Int? = null
    var kurs_name: String? = null
    var kurs_description: String? = null

    constructor()

    constructor(kurs_name: String?, kurs_description: String?) {
        this.kurs_name = kurs_name
        this.kurs_description = kurs_description
    }

    constructor(id: Int?, kurs_name: String?, kurs_description: String?) {
        this.id = id
        this.kurs_name = kurs_name
        this.kurs_description = kurs_description
    }




}