package com.cflower.lib_common.utils.extensions

import android.content.Context
import android.content.SharedPreferences

val Context.defaultSharedPreferences get() = sharedPreferences("share_data")

fun Context.sharedPreferences(name: String): SharedPreferences =
    getSharedPreferences(name, Context.MODE_PRIVATE)

fun SharedPreferences.getString(key: String) = getString(key, "") ?: ""
fun SharedPreferences.getInt(key: String) = getInt(key, 0)

inline fun SharedPreferences.editor(editorBuilder: SharedPreferences.Editor.() -> Unit) =
    edit().apply(editorBuilder).apply()