package com.ashishpatel.softwarelab.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ashishpatel.softwarelab.R
import com.ashishpatel.softwarelab.databinding.BusinessHoursRvItemBinding
import com.ashishpatel.softwarelab.screens.signup.models.BusinessHour

class BusinessHoursRvAdapter(
    private val itemClick : (BusinessHour) -> Unit
) : ListAdapter<BusinessHour, BusinessHoursRvAdapter.BusinessViewHolder>(DiffUtils()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BusinessViewHolder {
        val view = BusinessHoursRvItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return BusinessViewHolder(view,parent.context)
    }

    override fun onBindViewHolder(holder: BusinessViewHolder, position: Int) {
        getItem(position)?.let {item->
           holder.bindItem(item,itemClick)
        }
    }

    class BusinessViewHolder(val binding : BusinessHoursRvItemBinding,val context: Context) : RecyclerView.ViewHolder(binding.root) {
        fun bindItem(item : BusinessHour, itemClick : (BusinessHour) -> Unit){
            binding.text.text = item.hour
            if (item.selected){
//                binding.text.background = ContextCompat.getDrawable(context,R.drawable.business_hours_rounded_bg)
                binding.text.background = ContextCompat.getDrawable(context,R.drawable.business_hours_selected_rounded_bg)
            }else{
                binding.text.background = ContextCompat.getDrawable(context,R.drawable.business_hours_rounded_bg)
            }

            binding.root.setOnClickListener {
                itemClick(item)
            }
        }
    }

    class DiffUtils : DiffUtil.ItemCallback<BusinessHour>(){
        override fun areItemsTheSame(oldItem: BusinessHour, newItem: BusinessHour): Boolean {
            return oldItem.hour == newItem.hour && oldItem.day == newItem.day && oldItem.selected == newItem.selected
        }

        override fun areContentsTheSame(oldItem: BusinessHour, newItem: BusinessHour): Boolean {
            return oldItem == newItem
        }
    }
}