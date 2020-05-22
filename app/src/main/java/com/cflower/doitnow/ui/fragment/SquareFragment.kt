package com.cflower.doitnow.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.cflower.doitnow.R
import com.cflower.doitnow.ui.adapter.SquareRvAdapter
import com.cflower.doitnow.viewmodel.SquareViewModel
import com.cflower.lib_common.ui.BaseViewModelFragment
import kotlinx.android.synthetic.main.fragment_main_square.view.*

class SquareFragment : BaseViewModelFragment<SquareViewModel>() {
    override val layoutRes: Int = R.layout.fragment_main_square
    override val viewModelClass: Class<SquareViewModel> = SquareViewModel::class.java

    override fun View.onCreated(savedInstanceState: Bundle?) {
        val mAdapter = SquareRvAdapter {
            viewModel.load()
        }

        srl_square.setOnRefreshListener {
            mAdapter.refresh()
            viewModel.refresh()
        }

        rv_square.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = mAdapter
        }

        viewModel.apply {
            articles.observe {
                if (it != null) {
                    mAdapter.appendData(it)
                }
            }
            loading.observe {
                if (it == false) {
                    srl_square.isRefreshing = false
                }
            }
        }

        viewModel.load()
    }
}