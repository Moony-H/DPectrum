package com.example.dpectrum.adapter

import android.graphics.Bitmap
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.graphics.drawable.toBitmap
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dpectrum.R
import com.example.dpectrum.data.TutorContent
import com.example.dpectrum.databinding.SourceItemTutorContentBinding
import com.example.dpectrum.util.Util

class ContentAdapter(val onClick:(TutorContent,Bitmap)->Unit):
    ListAdapter<TutorContent, ContentAdapter.ViewHolder>(ContentDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            SourceItemTutorContentBinding.inflate(LayoutInflater.from(parent.context))
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }


    inner class ViewHolder(val binding:SourceItemTutorContentBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(content: TutorContent){
            Util.loadImage(content.imgSrc){
                binding.sourceItemTutorImage.setImageBitmap(it)
                binding.sourceItemTutorImage.scaleType=ImageView.ScaleType.FIT_CENTER
            }
            Log.d("image src",content.imgSrc)
            binding.sourceItemTutorName.text=content.tutorName
            binding.sourceItemTutorSchool.text=content.tutorSchool
            binding.root.setOnClickListener{
                onClick(content,binding.sourceItemTutorImage.drawable.toBitmap())
            }
        }
    }
}

class ContentDiffCallback : DiffUtil.ItemCallback<TutorContent>() {

    override fun areItemsTheSame(oldItem: TutorContent, newItem: TutorContent): Boolean {
        return oldItem.tutorName == newItem.tutorName
    }

    override fun areContentsTheSame(oldItem: TutorContent, newItem: TutorContent): Boolean {
        return oldItem == newItem
    }
}