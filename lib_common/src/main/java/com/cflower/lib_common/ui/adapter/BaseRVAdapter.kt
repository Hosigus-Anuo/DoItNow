package com.cflower.lib_common.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import java.util.*

/**
 * Create By Hosigus at 2020/5/20
 */

abstract class BaseRVAdapter<D> : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val datas: MutableList<D> = ArrayList()

    @get:LayoutRes
    protected abstract val dataLayout: Int

    protected val isFooterHolderCreator by lazy(LazyThreadSafetyMode.NONE) { this is FooterHolderCreator }

    protected abstract fun View.bindData(position: Int, data: D)

    fun appendData(dataList: List<D>) {
        datas.addAll(dataList)
        notifyItemRangeInserted(datas.size, dataList.size)
    }

    fun refresh(dataList: List<D>) {
        datas.clear()
        appendData(dataList)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == FOOTER) {
            (this as FooterHolderCreator).createFootHolder(parent)
        } else {
            DataHolder(LayoutInflater.from(parent.context).inflate(dataLayout, parent, false))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            FOOTER -> (holder as? FooterHolder)?.onFootLoad()
            else -> holder.itemView.bindData(position, datas[position])
        }
    }

    override fun getItemCount() = when {
        isFooterHolderCreator -> datas.size + 1
        else -> datas.size
    }

    override fun getItemViewType(position: Int) = when {
        position == itemCount - 1 && isFooterHolderCreator -> FOOTER
        else -> NORMAL
    }

    private class DataHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    abstract class FooterHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        abstract fun onFootLoad()
    }

    interface FooterHolderCreator {
        fun createFootHolder(parent: ViewGroup): FooterHolder
    }

    companion object {
        private const val NORMAL = 0x0
        private const val FOOTER = 0x1
    }

}