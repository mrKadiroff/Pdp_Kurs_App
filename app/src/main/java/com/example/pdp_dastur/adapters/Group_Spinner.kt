package com.example.pdp_dastur.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.example.pdp_dastur.databinding.SpinnerGroupBinding

class Group_Spinner(var list: ArrayList<String>) : BaseAdapter() {
    override fun getCount(): Int {
        return list.size
    }

    override fun getItem(position: Int): String {
        return list[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(p0: Int, convertView: View?, parent: ViewGroup?): View {
        var viewHolder:ViewHolder
        if (convertView==null){
            val binding =
                SpinnerGroupBinding.inflate(LayoutInflater.from(parent?.context),parent,false)
            viewHolder=ViewHolder(binding)
        }else{
            viewHolder=ViewHolder(SpinnerGroupBinding.bind(convertView))
        }
        viewHolder.spinnerGroupBinding.textSpinner.text=list[p0]
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