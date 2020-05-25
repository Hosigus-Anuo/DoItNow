package com.cflower.doitnow.ui.adapter

import android.view.View
import com.cflower.doitnow.R
import com.cflower.doitnow.model.flag.Flag
import com.cflower.lib_common.ui.adapter.BaseRVAdapter

/**
 * Create By Hosigus at 2020/5/25
 */
class FlagRvAdapter : BaseRVAdapter<Flag>() {
    override val dataLayout: Int = R.layout.item_flag

    override fun View.bindData(position: Int, data: Flag) {

    }

    public override fun getDataAt(position: Int): Flag {
        return super.getDataAt(position)
    }
}