package com.example.pdp_dastur.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.example.pdp_dastur.databinding.MentorRvBinding
import com.example.pdp_dastur.models.Mentor

class mentorAdapter(var list: ArrayList<Mentor>, var onItemClickListener: OnItemClickListener) : RecyclerView.Adapter<mentorAdapter.Vh>() {

    inner class Vh(var mentorRvBinding: MentorRvBinding) :
            RecyclerView.ViewHolder(mentorRvBinding.root){
                fun onBind(mentor: Mentor, position:Int){
                    mentorRvBinding.mentorName.text = (mentor.mentor_name+mentor.mentor_surname)
                    mentorRvBinding.mentorOtchestva.text = mentor.mentor_otch

                    mentorRvBinding.edit.setOnClickListener {
                        onItemClickListener.onEditClick(mentor, position, mentorRvBinding.edit)
                    }

                    mentorRvBinding.delete.setOnClickListener {
                        onItemClickListener.onDeleteClick(mentor, position, mentorRvBinding.delete)
                    }




                }
            }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
       return Vh (MentorRvBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(list[position], position)
    }

    override fun getItemCount(): Int = list.size

    interface OnItemClickListener{
        fun onEditClick(mentor: Mentor, position: Int, button: Button)
        fun onDeleteClick(mentor: Mentor, position: Int, button: Button)
    }
}