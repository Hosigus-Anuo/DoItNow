package com.cflower.lib_common.ui

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.cflower.lib_common.utils.DToast
import com.cflower.lib_common.utils.extensions.fillIntentArguments
import com.cflower.lib_common.viewmodel.BaseViewModel
import com.cflower.lib_common.viewmodel.event.ProgressDialogEvent

/**
 * Create By Hosigus at 2020/5/21
 */
abstract class BaseViewModelFragment<T : BaseViewModel> : Fragment() {
    @get:LayoutRes
    abstract val layoutRes: Int

    protected lateinit var viewModel: T

    protected abstract val viewModelClass: Class<T>

    private var progressDialog: ProgressDialog? = null

    private fun initProgressBar() = ProgressDialog(context).apply {
        isIndeterminate = true
        setMessage("Loading...")
        setOnDismissListener { viewModel.onProgressDialogDismissed() }
        show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModelFactory = getViewModelFactory()
        viewModel = if (viewModelFactory != null) {
            ViewModelProvider(this, viewModelFactory).get(viewModelClass)
        } else {
            ViewModelProvider(this).get(viewModelClass)
        }
        viewModel.apply {
            toastEvent.observe { str ->
                str?.let {
                    DToast.makeText(
                        context ?: return@observe,
                        it,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            longToastEvent.observe { str ->
                str?.let {
                    DToast.makeText(
                        context ?: return@observe,
                        it,
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
            progressDialogEvent.observe {
                it ?: return@observe
                //确保只有一个对话框会被弹出
                if (it != ProgressDialogEvent.DISMISS_DIALOG_EVENT && progressDialog?.isShowing != true) {
                    progressDialog = progressDialog ?: initProgressBar()
                    progressDialog?.show()
                } else if (it == ProgressDialogEvent.DISMISS_DIALOG_EVENT && progressDialog?.isShowing != false) {
                    progressDialog?.dismiss()
                }
            }
        }
    }

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

    protected open fun getViewModelFactory(): ViewModelProvider.Factory? = null

    inline fun <T> LiveData<T>.observe(crossinline onChange: (T?) -> Unit) =
        observe(this@BaseViewModelFragment, Observer { onChange(value) })

    inline fun <T> LiveData<T>.observeNotNull(crossinline onChange: (T) -> Unit) =
        observe(this@BaseViewModelFragment, Observer {
            it ?: return@Observer
            onChange(it)
        })

    override fun onDestroyView() {
        super.onDestroyView()
        if (progressDialog?.isShowing == true) {
            progressDialog?.dismiss()
        }
    }

    inline fun <reified T : Activity> startActivity(vararg params: Pair<String, Any?>) {
        startActivity(
            Intent(activity, T::class.java)
                .fillIntentArguments(params)
        )
    }
}