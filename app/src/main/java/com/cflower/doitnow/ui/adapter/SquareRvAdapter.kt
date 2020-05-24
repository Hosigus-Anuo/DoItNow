package com.cflower.doitnow.ui.adapter

import android.view.View
import com.cflower.doitnow.R
import com.cflower.doitnow.model.square.Article
import kotlinx.android.synthetic.main.item_square.view.*

/**
 * Create By Hosigus at 2020/5/22
 */
class SquareRvAdapter(val onLoad: () -> Unit) : FooterRVAdapter<Article>() {
    override val dataLayout: Int = R.layout.item_square

    override fun loadMore() {
        onLoad()
    }

    override fun View.bindData(position: Int, data: Article) {
        data.apply {
            tv_title_square.text = title
            tv_author_square.text = author
        }
    }
}