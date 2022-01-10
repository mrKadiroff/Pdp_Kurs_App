package com.example.pdp_dastur.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.example.pdp_dastur.databinding.GrListBinding
import com.example.pdp_dastur.models.Guruh
import com.example.pdp_dastur.models.Talaba




class Group_Rv(var list: List<Guruh>,var onItemClickListener: OnItemClickListener) :RecyclerView.Adapter<Group_Rv.Vh>(){

    inner class Vh(var grListBinding: GrListBinding) : RecyclerView.ViewHolder(grListBinding.root){
        fun onBind(guruh: Guruh){



            grListBinding.grName.text = guruh.gr_name
            grListBinding.startt.setOnClickListener {
                onItemClickListener.onItemStartClick(guruh, position, grListBinding.startt)
            }
            grListBinding.editt.setOnClickListener {
                onItemClickListener.onItemEditClick(guruh,position, grListBinding.editt)
            }
            grListBinding.delete.setOnClickListener {
                onItemClickListener.onItemDeleteClick(guruh,position,grListBinding.delete)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(GrListBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(list[position])
    }

    override fun getItemCount(): Int = list.size

    interface OnItemClickListener{
        fun onItemStartClick(guruh: Guruh,position: Int, button: Button)
        fun onItemEditClick(guruh: Guruh,position: Int,button: Button)
        fun onItemDeleteClick(guruh: Guruh,position: Int,button: Button)
    }
}