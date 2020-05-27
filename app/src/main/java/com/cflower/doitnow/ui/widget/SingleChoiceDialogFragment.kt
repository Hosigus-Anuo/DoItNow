package com.cflower.doitnow.ui.widget

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager


class SingleChoiceDialogFragment : DialogFragment() {
    private var title: String? = null
    private lateinit var items: Array<String>
    private var onClickListener: DialogInterface.OnClickListener? = null
    private var positiveCallback: DialogInterface.OnClickListener? = null
    fun show(
        title: String?,
        items: Array<String>,
        onClickListener: DialogInterface.OnClickListener?,
        positiveCallback: DialogInterface.OnClickListener?,
        fragmentManager: FragmentManager?
    ) {
        this.title = title
        this.items = items
        this.onClickListener = onClickListener
        this.positiveCallback = positiveCallback
        if (fragmentManager != null) {
            show(fragmentManager, "SingleChoiceDialogFragment")
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder: AlertDialog.Builder = AlertDialog.Builder(requireContext())
        builder.setTitle(title).setSingleChoiceItems(items, 0, onClickListener)
            .setPositiveButton("确定", positiveCallback)
        return builder.create()
    }
}