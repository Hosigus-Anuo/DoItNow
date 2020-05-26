package com.cflower.doitnow.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.cflower.doitnow.R
import com.cflower.doitnow.ui.adapter.FlagRvAdapter
import com.cflower.doitnow.ui.widget.FlagRvDecoration
import com.cflower.lib_common.ui.BaseFragment
import kotlinx.android.synthetic.main.fragment_flag.view.*

class FlagFragment : BaseFragment() {
    override val layoutRes: Int = R.layout.fragment_flag
    private val mAdapter = FlagRvAdapter()

    override fun View.onCreated(savedInstanceState: Bundle?) {
        rv_flag.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = mAdapter
            addItemDecoration(FlagRvDecoration(context, mAdapter))
        }
    }
}