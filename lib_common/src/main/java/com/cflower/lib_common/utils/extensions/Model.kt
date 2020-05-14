package com.cflower.lib_common.utils.extensions

import com.cflower.lib_common.model.ModelWrapper

/**
 * Create By Hosigus at 2020/5/14
 */
val ModelWrapper<*>.isResponseOK: Boolean
    get() = status == 200

