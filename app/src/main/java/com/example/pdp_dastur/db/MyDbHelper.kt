package com.example.pdp_dastur.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.pdp_dastur.models.Guruh
import com.example.pdp_dastur.models.Kurs
import com.example.pdp_dastur.models.Mentor
import com.example.pdp_dastur.models.Talaba
import com.example.pdp_dastur.utils.Constant

class MyDbHelper(context: Context) :
    SQLiteOpenHelper(context,Constant.DATABASE_NAME,null, Constant.DATABASE_VERSION),
    DatabaseService{
    override fun onCreate(db: SQLiteDatabase?) {
        val courseTableQuery =
            "CREATE TABLE ${Constant.KURS_TABLE} (${Constant.KURS_ID} INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE, ${Constant.KURS_NOMI} TEXT NOT NULL, ${Constant.KURS_HAQIDA} TEXT NOT NULL)"
        val studentTableQuery =
            "CREATE TABLE ${Constant.TALABA_TABLE} (${Constant.TALABA_ID} INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE, ${Constant.TALABA_FAMILIYA} TEXT NOT NULL, ${Constant.TALABA_ISMI} TEXT NOT NULL, ${Constant.TALABA_OTCHESTVA} TEXT NOT NULL, ${Constant.TALABA_SANA} NUMERIC NOT NULL, ${Constant.TALABA_GURUH_ID} INTEGER NOT NULL, FOREIGN KEY(${Constant.TALABA_GURUH_ID}) REFERENCES ${Constant.GURUH_TABLE} (${Constant.GURUH_ID}))"
        val groupTableQuery =
            "CREATE TABLE ${Constant.GURUH_TABLE} (${Constant.GURUH_ID} INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE, ${Constant.GR_NOMI} TEXT NOT NULL, ${Constant.GR_KURS_ID} INTEGER NOT NULL, ${Constant.GR_MENTOR_ID} INTEGER NOT NULL, ${Constant.GR_VAQT} TEXT NOT NULL, ${Constant.GR_KUNLARI} TEXT NOT NULL, ${Constant.GR_OPEN} TEXT NOT NULL, FOREIGN KEY(${Constant.GR_KURS_ID}) REFERENCES ${Constant.KURS_TABLE} (${Constant.KURS_ID}), FOREIGN KEY(${Constant.GR_MENTOR_ID}) REFERENCES ${Constant.MENTOR_TABLE} (${Constant.MENTOR_ID}))"
        val mentorTableQuery =
            "CREATE TABLE ${Constant.MENTOR_TABLE} (${Constant.MENTOR_ID} INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE, ${Constant.MENTOR_FAMILIYA} TEXT NOT NULL, ${Constant.MENTOR_ISM} TEXT NOT NULL, ${Constant.MENTOR_OTCHESTVA} TEXT NOT NULL, ${Constant.MENTOR_KURS} INTEGER NOT NULL, FOREIGN KEY(${Constant.MENTOR_KURS}) REFERENCES ${Constant.KURS_TABLE} (${Constant.KURS_ID}))"

        db?.execSQL(courseTableQuery)
        db?.execSQL(studentTableQuery)
        db?.execSQL(groupTableQuery)
        db?.execSQL(mentorTableQuery)

    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }

    override fun insertCourse(kurs: Kurs) {
        val database = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(Constant.KURS_NOMI, kurs.kurs_name)
        contentValues.put(Constant.KURS_HAQIDA, kurs.kurs_description)
        database.insert(Constant.KURS_TABLE,null,contentValues)
        database.close()
    }

    override fun insertStudent(talaba: Talaba) {
        val database = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(Constant.TALABA_FAMILIYA, talaba.talaba_surname)
        contentValues.put(Constant.TALABA_ISMI, talaba.talaba_name)
        contentValues.put(Constant.TALABA_OTCHESTVA, talaba.talaba_otch)
        contentValues.put(Constant.TALABA_SANA, talaba.talaba_date)
        contentValues.put(Constant.TALABA_GURUH_ID, talaba.talaba_guruh?.id)
        database.insert(Constant.TALABA_TABLE,null,contentValues)
        database.close()
    }

    override fun insertGroup(guruh: Guruh) {
        val database = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(Constant.GR_NOMI, guruh.gr_name)
        contentValues.put(Constant.GR_KURS_ID, guruh.gr_kurs_id?.id)
        contentValues.put(Constant.GR_MENTOR_ID, guruh.gr_mentor_id?.id)
        contentValues.put(Constant.GR_VAQT, guruh.gr_time)
        contentValues.put(Constant.GR_KUNLARI, guruh.gr_days)
        contentValues.put(Constant.GR_OPEN, guruh.gr_open)
        database.insert(Constant.GURUH_TABLE, null, contentValues)
        database.close()
    }

    override fun insertMentor(mentor: Mentor) {
        val database = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(Constant.MENTOR_FAMILIYA, mentor.mentor_surname)
        contentValues.put(Constant.MENTOR_ISM, mentor.mentor_name)
        contentValues.put(Constant.MENTOR_OTCHESTVA, mentor.mentor_otch)
        contentValues.put(Constant.MENTOR_KURS, mentor.mentor_kurs_id?.id)
        database.insert(Constant.MENTOR_TABLE, null, contentValues)
        database.close()
    }

    override fun getAllCourse(): ArrayList<Kurs> {
        var list = ArrayList<Kurs>()
        val query = "select * from ${Constant.KURS_TABLE}"
        val database = this.readableDatabase
        val cursor = database.rawQuery(query, null)

        if (cursor.moveToFirst()) {
            do {
                val kurs = Kurs()
                kurs.id = cursor.getInt(0)
                kurs.kurs_name = cursor.getString(1)
                kurs.kurs_description = cursor.getString(2)
                list.add(kurs)
            }while (cursor.moveToNext())
        }
        return list
    }

    override fun getAllStudent(): ArrayList<Talaba> {
        val list = ArrayList<Talaba>()
        val query = "select * from ${Constant.TALABA_TABLE}"
        val database = this.readableDatabase
        val cursor = database.rawQuery(query, null)

        if (cursor.moveToFirst()) {
            do {
                val talaba = Talaba()
                talaba.id = cursor.getInt(0)
                talaba.talaba_surname = cursor.getString(1)
                talaba.talaba_name = cursor.getString(2)
                talaba.talaba_otch = cursor.getString(3)
                talaba.talaba_date = cursor.getString(4)
                talaba.talaba_guruh = getGroupById(cursor.getInt(5))
                list.add(talaba)
            }while (cursor.moveToNext())
        }
        return list
    }

    override fun getAllGroup(): ArrayList<Guruh> {
        val list = ArrayList<Guruh>()
        val query = "select * from ${Constant.GURUH_TABLE}"
        val database = this.readableDatabase
        val cursor = database.rawQuery(query, null)

        if (cursor.moveToFirst()) {
            do {
                val guruh = Guruh()
                guruh.id = cursor.getInt(0)
                guruh.gr_name = cursor.getString(1)
                guruh.gr_kurs_id = getCourseById(cursor.getInt(2))
                guruh.gr_mentor_id = getMentorById(cursor.getInt(3))
                guruh.gr_time = cursor.getString(4)
                guruh.gr_days = cursor.getString(5)
                guruh.gr_open = cursor.getString(6)
                list.add(guruh)
            }while (cursor.moveToNext())
        }
        return list
    }

    override fun getAllMentors(): ArrayList<Mentor> {
        val list = ArrayList<Mentor>()
        val query = "select * from ${Constant.MENTOR_TABLE}"
        val database = this.readableDatabase
        val cursor = database.rawQuery(query, null)

        if (cursor.moveToFirst()) {
            do {
                val mentor = Mentor()
                mentor.id = cursor.getInt(0)
                mentor.mentor_surname = cursor.getString(1)
                mentor.mentor_name = cursor.getString(2)
                mentor.mentor_otch = cursor.getString(3)
                mentor.mentor_kurs_id = getCourseById(cursor.getInt(4))
                list.add(mentor)
            }while (cursor.moveToNext())
        }
        return list
    }

    override fun getCourseById(id: Int): Kurs {
        val database = this.readableDatabase
        val cursor = database.query(
            Constant.KURS_TABLE,
            arrayOf(
                Constant.KURS_ID,
                Constant.KURS_NOMI,
                Constant.KURS_HAQIDA
            ),
            "${Constant.KURS_ID} = ?",
            arrayOf(id.toString()),
            null,
            null,
            null
        )
        cursor?.moveToFirst()
        val kurs = Kurs()
        kurs.id = cursor.getInt(0)
        kurs.kurs_name = cursor.getString(1)
        kurs.kurs_description = cursor.getString(2)
        return kurs
    }

    override fun getGroupById(id: Int): Guruh {
        var guruh=Guruh()
        val database = this.readableDatabase
        val cursor = database.query(
            Constant.GURUH_TABLE,
            arrayOf(
                Constant.GURUH_ID,
                Constant.GR_NOMI,
                Constant.GR_KURS_ID,
                Constant.GR_MENTOR_ID,
                Constant.GR_VAQT,
                Constant.GR_KUNLARI,
                Constant.GR_OPEN
            ),
            "${Constant.GURUH_ID} = ?",
            arrayOf("$id"),
            null,
            null,
            null,
            null
        )
        if (cursor!=null && cursor.count>0){
            cursor.moveToFirst()
            guruh.id = cursor.getInt(0)
            guruh.gr_name = cursor.getString(1)
            guruh.gr_kurs_id = getCourseById(cursor.getInt(2))
            guruh.gr_mentor_id = getMentorById(cursor.getInt(3))
            guruh.gr_time = cursor.getString(4)
            guruh.gr_days = cursor.getString(5)
            guruh.gr_open = cursor.getString(6)

        }

        return guruh
    }

    override fun getGroupByType(isOpen: String): ArrayList<Guruh> {
        var list = ArrayList<Guruh>()

        return list
    }

    override fun getMentorById(id: Int): Mentor {
        val mentor = Mentor()
        val database = this.readableDatabase
        val cursor = database.query(
            Constant.MENTOR_TABLE,
            arrayOf(
                Constant.MENTOR_ID,
                Constant.MENTOR_FAMILIYA,
                Constant.MENTOR_ISM,
                Constant.MENTOR_OTCHESTVA,
                Constant.MENTOR_KURS


            ),
            "${Constant.MENTOR_ID} = ?",
            arrayOf(id.toString()),
            null,
            null,
            null
        )
        if (cursor!=null && cursor.count>0){
            cursor.moveToFirst()
            mentor.id = cursor.getInt(0)
            mentor.mentor_surname = cursor.getString(1)
            mentor.mentor_name = cursor.getString(2)
            mentor.mentor_otch = cursor.getString(3)
            mentor.mentor_kurs_id = getCourseById(cursor.getInt(4))
        }
        return mentor
    }

    override fun updateStudent(talaba: Talaba): Int {
        val database = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(Constant.TALABA_ID, talaba.id)
        contentValues.put(Constant.TALABA_FAMILIYA, talaba.talaba_surname)
        contentValues.put(Constant.TALABA_ISMI, talaba.talaba_name)
        contentValues.put(Constant.TALABA_OTCHESTVA, talaba.talaba_otch)
        contentValues.put(Constant.TALABA_SANA, talaba.talaba_date)
        contentValues.put(Constant.TALABA_GURUH_ID, talaba.talaba_guruh?.id)
        return database.update(
            Constant.TALABA_TABLE,
            contentValues,
            "${Constant.TALABA_ID} = ?",
            arrayOf(talaba.id.toString())
        )
    }

    override fun updateGroup(guruh: Guruh): Int {
        val database = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(Constant.GURUH_ID, guruh.id)
        contentValues.put(Constant.GR_NOMI, guruh.gr_name)
        contentValues.put(Constant.GR_KURS_ID, guruh.gr_kurs_id?.id)
        contentValues.put(Constant.GR_MENTOR_ID, guruh.gr_mentor_id?.id)
        contentValues.put(Constant.GR_VAQT, guruh.gr_time)
        contentValues.put(Constant.GR_KUNLARI, guruh.gr_days)
        contentValues.put(Constant.GR_OPEN, guruh.gr_open)
        return database.update(
            Constant.GURUH_TABLE,
            contentValues,
            "${Constant.GURUH_ID}=?",
            arrayOf("${guruh.id}")
        )
    }

    override fun updateMentor(mentor: Mentor): Int {
        val database = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(Constant.MENTOR_ID, mentor.id)
        contentValues.put(Constant.MENTOR_FAMILIYA, mentor.mentor_surname)
        contentValues.put(Constant.MENTOR_ISM, mentor.mentor_name)
        contentValues.put(Constant.MENTOR_OTCHESTVA, mentor.mentor_otch)
        return database.update(
            Constant.MENTOR_TABLE,
            contentValues,
            "${Constant.MENTOR_ID} = ?",
            arrayOf(mentor.id.toString())
        )
    }

    override fun updateCourse(kurs: Kurs): Int {
        val database = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(Constant.KURS_ID, kurs.id)
        contentValues.put(Constant.KURS_NOMI, kurs.kurs_name)
        contentValues.put(Constant.KURS_HAQIDA, kurs.kurs_description)
        return database.update(
            Constant.KURS_TABLE,
            contentValues,
            "${Constant.KURS_ID} = ?",
            arrayOf(kurs.id.toString())
        )
    }

    override fun deleteStudent(talaba: Talaba) {
        val database = this.writableDatabase
        database.delete(Constant.TALABA_TABLE, "${Constant.TALABA_ID} = ?", arrayOf("${talaba.id}"))
        database.close()
    }

    override fun deleteStudentsBygroup(guruh: Guruh) {
        val db = this.writableDatabase
        db.delete("talaba", "tb_guruh=?", arrayOf("${guruh}"))
        db.close()
    }

    override fun deleteGroup(guruh: Guruh) {
        val database = this.writableDatabase
        database.delete(Constant.GURUH_TABLE, "${Constant.GURUH_ID} = ?", arrayOf("${guruh.id}"))
        database.close()
    }

    override fun deleteMentor(mentor: Mentor) {
        val database = this.writableDatabase
        database.delete(Constant.MENTOR_TABLE, "${Constant.MENTOR_ID} = ?", arrayOf("${mentor.id}"))
        database.close()
    }

    override fun deleteCourse(kurs: Kurs) {
        val database = this.writableDatabase
        database.delete(Constant.KURS_TABLE, "${Constant.KURS_ID} = ?", arrayOf("${kurs.id}"))
        database.close()
    }
}