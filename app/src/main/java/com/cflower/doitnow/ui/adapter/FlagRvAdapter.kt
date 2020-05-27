package com.cflower.doitnow.ui.adapter

import android.view.View
import com.cflower.doitnow.R
import com.cflower.doitnow.model.flag.Flag
import com.cflower.lib_common.ui.adapter.BaseRVAdapter
import kotlinx.android.synthetic.main.item_flag.view.*

/**
 * Create By Hosigus at 2020/5/25
 */
class FlagRvAdapter : BaseRVAdapter<Flag>() {
    override val dataLayout: Int = R.layout.item_flag

    override fun appendData(dataList: List<Flag>) {
        super.appendData(dataList)
    }

    override fun View.bindData(position: Int, data: Flag) {
        tv_title_flag.text = data.title
        iv_tag_flag.setImageResource(data.tag.drawable)
    }

    override fun getItemViewType(position: Int): Int {
        return getDataAt(position).type.ordinal
    }
}