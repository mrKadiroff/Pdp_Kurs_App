package com.example.pdp_dastur.models

import java.io.Serializable

class Talaba : Serializable{
    var id: Int? = null
    var talaba_surname: String? = null
    var talaba_name: String? = null
    var talaba_otch: String? = null
    var talaba_date: String? = null
    var talaba_guruh: Guruh? = null

    constructor()
    constructor(
        talaba_surname: String?,
        talaba_name: String?,
        talaba_otch: String?,
        talaba_date: String?,
        talaba_guruh: Guruh?
    ) {
        this.talaba_surname = talaba_surname
        this.talaba_name = talaba_name
        this.talaba_otch = talaba_otch
        this.talaba_date = talaba_date
        this.talaba_guruh = talaba_guruh
    }

    constructor(
        id: Int?,
        talaba_surname: String?,
        talaba_name: String?,
        talaba_otch: String?,
        talaba_date: String?,
        talaba_guruh: Guruh?
    ) {
        this.id = id
        this.talaba_surname = talaba_surname
        this.talaba_name = talaba_name
        this.talaba_otch = talaba_otch
        this.talaba_date = talaba_date
        this.talaba_guruh = talaba_guruh
    }


}