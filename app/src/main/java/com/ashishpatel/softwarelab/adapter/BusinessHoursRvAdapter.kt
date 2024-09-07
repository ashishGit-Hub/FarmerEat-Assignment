package com.ashishpatel.softwarelab.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ashishpatel.softwarelab.databinding.BusinessHoursRvItemBinding

class BusinessHoursRvAdapter : ListAdapter<String, BusinessHoursRvAdapter.BusinessViewHolder>(DiffUtils()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BusinessViewHolder {
        val view = BusinessHoursRvItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return BusinessViewHolder(view)
    }

    override fun onBindViewHolder(holder: BusinessViewHolder, position: Int) {
        getItem(position)?.let {
            holder.binding.text.text = it
        }
    }


    class BusinessViewHolder(val binding : BusinessHoursRvItemBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    class DiffUtils : DiffUtil.ItemCallback<String>(){
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }
    }
}