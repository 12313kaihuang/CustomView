package com.yu.hu.libbase.base

import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import java.lang.Exception

/**
 * @auther hy
 * create on 2021/07/30 下午3:32
 */
open class BaseItemAdapter :
    ListAdapter<BaseItemAdapter.IBaseItem, BaseItemAdapter.BaseHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseHolder {
        val btn = Button(parent.context).apply {
            isAllCaps = false
            layoutParams = RecyclerView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        }
        return BtnHolder(btn)
    }

    override fun onBindViewHolder(holder: BaseHolder, position: Int) {
        holder.bindItem(getItem(position))
    }

    override fun getItemViewType(position: Int): Int {
        return -1
    }

    abstract class BaseHolder(root: View) : RecyclerView.ViewHolder(root) {
        abstract fun bindItem(baseItem: IBaseItem)
    }

    class BtnHolder(private val btn: Button) : BaseHolder(btn) {
        override fun bindItem(baseItem: IBaseItem) {
            val item = baseItem as BtnItem
            btn.text = item.text
            btn.isAllCaps = false
            btn.setOnClickListener {
                try {
                    item.clickListener?.invoke()
                } catch (e: Exception) {
                    Toast.makeText(it.context, "click error", Toast.LENGTH_SHORT).show()
                    Log.e("BtnAdapter", "btn(${item.text}) onLick error ", e)
                }
            }
        }
    }

    interface IBaseItem

    data class BtnItem(val text: String, val clickListener: (() -> Unit)?) : IBaseItem

    class DiffCallback : DiffUtil.ItemCallback<IBaseItem>() {
        override fun areItemsTheSame(oldItem: IBaseItem, newItem: IBaseItem): Boolean =
            oldItem === newItem

        override fun areContentsTheSame(oldItem: IBaseItem, newItem: IBaseItem): Boolean =
            oldItem.equals(newItem)

    }
}