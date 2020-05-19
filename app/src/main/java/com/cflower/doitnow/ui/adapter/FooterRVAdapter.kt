package com.cflower.doitnow.ui.adapter

import android.view.View
import android.view.ViewGroup
import com.cflower.doitnow.ui.widget.RvFooter
import com.cflower.lib_common.ui.adapter.BaseRVAdapter

/**
 * Create By Hosigus at 2020/5/20
 */
abstract class FooterRVAdapter<D> : BaseRVAdapter<D>(), BaseRVAdapter.FooterHolderCreator {
    abstract fun loadMore()

    protected open fun createFooterView(parent: ViewGroup): View {
        return RvFooter(parent.context)
    }

    override fun createFootHolder(parent: ViewGroup): BaseRVAdapter.FooterHolder {
        return FooterHolder(createFooterView(parent))
    }

    inner class FooterHolder(view: View) : BaseRVAdapter.FooterHolder(view) {
        override fun onFootLoad() {
            loadMore()
        }
    }
}