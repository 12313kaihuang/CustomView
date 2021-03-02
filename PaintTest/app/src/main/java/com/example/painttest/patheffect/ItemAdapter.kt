package com.example.painttest.patheffect

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.painttest.R
import com.example.painttest.databinding.ItemPathEffectBinding

class ItemAdapter : ListAdapter<Int, ItemAdapter.ViewHolder>(Diff()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflate = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_path_effect, parent, false)
        return ViewHolder(inflate)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val effectView = holder.viewBinding.pathEffectView
        effectView.setType(getItem(position))
        holder.viewBinding.effectTypeTv.text = effectView.typeText
    }

    class ViewHolder(rootView: View) : RecyclerView.ViewHolder(rootView) {
        val viewBinding: ItemPathEffectBinding = ItemPathEffectBinding.bind(rootView)
    }

    class Diff : DiffUtil.ItemCallback<Int>() {
        override fun areItemsTheSame(oldItem: Int, newItem: Int): Boolean = oldItem == newItem

        override fun areContentsTheSame(oldItem: Int, newItem: Int): Boolean = oldItem == newItem

    }
}