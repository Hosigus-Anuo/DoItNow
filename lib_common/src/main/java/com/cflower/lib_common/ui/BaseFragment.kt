package com.cflower.lib_common.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.cflower.lib_common.utils.extensions.fillIntentArguments

/**
 * Create By Hosigus at 2020/5/25
 */
abstract class BaseFragment : Fragment() {
    @get:LayoutRes
    abstract val layoutRes: Int

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(layoutRes,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.onCreated(savedInstanceState)
    }

    protected abstract fun View.onCreated(savedInstanceState: Bundle?)

    inline fun <reified T : Activity> startActivity(vararg params: Pair<String, Any?>) {
        startActivity(
            Intent(activity, T::class.java)
                .fillIntentArguments(params)
        )
    }
}