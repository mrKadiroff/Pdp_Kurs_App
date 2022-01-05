package com.example.pdp_dastur.db

import com.example.pdp_dastur.models.Guruh
import com.example.pdp_dastur.models.Kurs
import com.example.pdp_dastur.models.Mentor
import com.example.pdp_dastur.models.Talaba

interface DatabaseService {

    fun insertCourse(kurs: Kurs)
    fun insertStudent(talaba: Talaba)
    fun insertGroup(guruh: Guruh)
    fun insertMentor(mentor: Mentor)

    fun getAllCourse(): ArrayList<Kurs>
    fun getAllStudent(): ArrayList<Talaba>
    fun getAllGroup(): ArrayList<Guruh>
    fun getAllMentors(): ArrayList<Mentor>

    fun getCourseById(id: Int): Kurs
    fun getGroupById(id: Int): Guruh
    fun getGroupByType(isOpen:String):ArrayList<Guruh>
    fun getMentorById(id: Int): Mentor

    fun updateStudent(talaba: Talaba):Int
    fun updateGroup(guruh: Guruh):Int
    fun updateMentor(mentor: Mentor):Int
    fun updateCourse(kurs: Kurs):Int

    fun deleteStudent(talaba: Talaba)
    fun deleteGroup(guruh: Guruh)
    fun deleteMentor(mentor: Mentor)
    fun deleteCourse(kurs: Kurs)
}