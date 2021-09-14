package com.rankor.breakingbadapp.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rankor.breakingbadapp.R
import com.rankor.breakingbadapp.databinding.ItemCharacterBinding
import com.rankor.breakingbadapp.ui.entities.BBCharacterItem

class CharacterAdapter(
    private val context: Context,
    private val onClick: (BBCharacterItem) -> Unit
) : RecyclerView.Adapter<CharacterAdapter.ViewHolder>() {

    private var dataSet = emptyList<BBCharacterItem>()
    var isClickAllowed = true

    class ViewHolder(val binding: ItemCharacterBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemCharacterBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val item = dataSet[position]
        with(viewHolder.binding){
            tvCharacterName.text = item.name
            Glide.with(context)
                .load(item.img)
                .override(64, 64)
                .centerCrop()
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(ivCharacterAvatar)

            mainContainer.setOnClickListener {
                if (isClickAllowed) {
                    isClickAllowed = false
                    onClick.invoke(item)
                }
            }
        }
    }

    override fun getItemCount() = dataSet.size

    fun setData(newDataSet: List<BBCharacterItem>) {
        dataSet = newDataSet
        notifyItemRangeChanged(0, dataSet.size - 1)
    }
}