package com.example.pdp_dastur.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.example.pdp_dastur.databinding.MentorRvBinding
import com.example.pdp_dastur.databinding.StListBinding
import com.example.pdp_dastur.models.Mentor
import com.example.pdp_dastur.models.Talaba

class Talaba_Adapter (var list: ArrayList<Talaba>, var onItemClickListener: OnItemClickListener) : RecyclerView.Adapter<Talaba_Adapter.Vh>() {

    inner class Vh(var stListBinding: StListBinding) :
        RecyclerView.ViewHolder(stListBinding.root){
        fun onBind(talaba: Talaba, position:Int){
            stListBinding.ismss.text = (talaba.talaba_surname+talaba.talaba_name)
            stListBinding.otests.text = talaba.talaba_otch
            stListBinding.sanana.text = talaba.talaba_date

            stListBinding.edit.setOnClickListener {
                onItemClickListener.onEditClick(talaba, position, stListBinding.edit)
            }

            stListBinding.delete.setOnClickListener {
                onItemClickListener.onDeleteClick(talaba, position, stListBinding.delete)
            }




        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh (StListBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(list[position], position)
    }

    override fun getItemCount(): Int = list.size

    interface OnItemClickListener{
        fun onEditClick(talaba: Talaba, position: Int, button: Button)
        fun onDeleteClick(talaba: Talaba, position: Int, button: Button)
    }
}