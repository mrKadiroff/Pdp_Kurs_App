package com.example.pdp_dastur.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.example.pdp_dastur.databinding.SpinnerGroupBinding
import com.example.pdp_dastur.models.Guruh

class Group_Spinner(var list: List<Guruh>) : BaseAdapter() {
    override fun getCount(): Int {
        return list.size
    }

    override fun getItem(position: Int): Guruh {
        return list[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var viewHolder:ViewHolder
        if (convertView==null){
            val binding =
                SpinnerGroupBinding.inflate(LayoutInflater.from(parent?.context),parent,false)
            viewHolder=ViewHolder(binding)
        }else{
            viewHolder=ViewHolder(SpinnerGroupBinding.bind(convertView))
        }
        viewHolder.spinnerGroupBinding.textSpinner.text=list[position].gr_name
        return viewHolder.itemView
    }

    inner class ViewHolder{
        var itemView:View
        var spinnerGroupBinding:SpinnerGroupBinding

        constructor(spinnerGroupBinding: SpinnerGroupBinding) {
            this.itemView = spinnerGroupBinding.root
            this.spinnerGroupBinding = spinnerGroupBinding
        }
    }
}