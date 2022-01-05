package com.example.pdp_dastur.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.example.pdp_dastur.databinding.SpinnerGroupBinding

class TimeSpinner (var list: ArrayList<String>) : BaseAdapter() {

    override fun getCount(): Int {
        return list.size
    }

    override fun getItem(position: Int): String {
        return list[position]
    }

    override fun getItemId(position: Int): Long {
        return  position.toLong()
    }

    override fun getView(position: Int, convertBiew: View?, parent: ViewGroup?): View {
        var timeViewHolder: TimeViewHolder
        if (convertBiew == null) {
            var binding =
                SpinnerGroupBinding.inflate(LayoutInflater.from(parent?.context), parent, false)
            timeViewHolder = TimeViewHolder(binding)
        } else {
            timeViewHolder = TimeViewHolder(SpinnerGroupBinding.bind(convertBiew))
        }
        timeViewHolder.spinnerGroupBinding.textSpinner.text = list[position]
        return timeViewHolder.itemView
    }

    inner class TimeViewHolder {
        var itemView: View
        var spinnerGroupBinding: SpinnerGroupBinding

        constructor(spinnerGroupBinding: SpinnerGroupBinding) {
            itemView = spinnerGroupBinding.root
            this.spinnerGroupBinding = spinnerGroupBinding
        }
    }

}