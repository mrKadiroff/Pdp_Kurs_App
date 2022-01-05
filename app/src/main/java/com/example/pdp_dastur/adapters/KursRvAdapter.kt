package com.example.pdp_dastur.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pdp_dastur.databinding.KursRvBinding
import com.example.pdp_dastur.models.Guruh
import com.example.pdp_dastur.models.Kurs
import com.example.pdp_dastur.models.Mentor
import com.example.pdp_dastur.models.Talaba

class KursRvAdapter(var list: ArrayList<Kurs>,var ItemOnClickListener: OnClickListener) : RecyclerView.Adapter<KursRvAdapter.Vh>() {

    var groupList: ArrayList<Guruh>? = null
    fun setGroup(groupList: ArrayList<Guruh>) {
        this.groupList = groupList
    }

    var mentorList: ArrayList<Mentor>? = null
    fun setMentor(mentorList: ArrayList<Mentor>) {
        this.mentorList = mentorList
    }

    var studentList: ArrayList<Talaba>? = null
    fun setStudent(studentList: ArrayList<Talaba>){
        this.studentList = studentList
    }

    inner class Vh(var kursRvBinding: KursRvBinding) :
            RecyclerView.ViewHolder(kursRvBinding.root){
                fun onBind(kurs: Kurs, position: Int) {
                    kursRvBinding.courseNameTv.text = kurs.kurs_name

                    kursRvBinding.root.setOnClickListener {
                        ItemOnClickListener.OnItemClick(kurs, position)
                    }
                }
            }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(KursRvBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(list[position], position)
    }

    override fun getItemCount(): Int = list.size

    interface OnClickListener{
        fun OnItemClick(kurs: Kurs,position: Int)
    }
}