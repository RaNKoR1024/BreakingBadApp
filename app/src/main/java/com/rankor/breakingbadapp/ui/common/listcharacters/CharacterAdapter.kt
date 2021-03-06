package com.rankor.breakingbadapp.ui.common.listcharacters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.rankor.breakingbadapp.R
import com.rankor.breakingbadapp.databinding.ItemCharacterBinding
import jp.wasabeef.glide.transformations.CropTransformation

class CharacterAdapter(
    private val context: Context,
    private val onClick: (CharacterItem) -> Unit
) : RecyclerView.Adapter<CharacterAdapter.ViewHolder>() {

    private var dataSet = emptyList<CharacterItem>()
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
                .apply(
                    RequestOptions.bitmapTransform(
                        CropTransformation(64, 64, CropTransformation.CropType.TOP)
                    )
                ).placeholder(R.drawable.ic_baseline_image)
                .error(R.drawable.ic_baseline_error_outline)
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

    fun setData(newDataSet: List<CharacterItem>) {
        dataSet = newDataSet
        notifyItemRangeChanged(0, dataSet.size - 1)
    }
}