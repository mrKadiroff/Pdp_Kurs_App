package com.example.pdp_dastur.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.example.pdp_dastur.databinding.MentorItemBinding
import com.example.pdp_dastur.models.Mentor

class Mentor_Spinner(var list: List<Mentor>):BaseAdapter() {
    override fun getCount(): Int {
        return list.size
    }

    override fun getItem(position: Int): Mentor {
        return list[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var mentorViewHolder: MentorViewHolder
        if (convertView == null) {
            val binding =
                MentorItemBinding.inflate(LayoutInflater.from(parent?.context), parent, false)
            mentorViewHolder = MentorViewHolder(binding)
        } else {
           mentorViewHolder = MentorViewHolder(MentorItemBinding.bind(convertView))
        }
        mentorViewHolder.mentorItemBinding.mentori.text = list[position].mentor_name
        return mentorViewHolder.itemView
    }

    inner class MentorViewHolder {
        val itemView: View
        var mentorItemBinding: MentorItemBinding


        constructor(mentorItemBinding: MentorItemBinding) {
            itemView = mentorItemBinding.root
            this.mentorItemBinding = mentorItemBinding
        }
    }
}