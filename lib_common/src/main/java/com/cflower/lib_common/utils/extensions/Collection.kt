package com.cflower.lib_common.utils.extensions

/**
 * Create By Hosigus at 2020/5/24
 */
inline fun <T> Iterable<T>.forEachObject(action: T.() -> Unit) {
    for (element in this) element.action()
}