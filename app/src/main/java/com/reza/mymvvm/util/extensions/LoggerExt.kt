

package com.reza.mymvvm.util.extensions

import com.reza.mymvvm.BuildConfig
import timber.log.Timber


/**
 * Used to determine whether the logging is enabled or not
 */
internal var isLoggingEnabled: Boolean = BuildConfig.DEBUG

internal inline fun <reified T : Any> T.logError(log: String) {
    if (isLoggingEnabled) {
        Timber.e(log)
    }
}

internal inline fun <reified T : Any> T.logError(throwable: Throwable) {
    if (isLoggingEnabled) {
         Timber.e(throwable.message.toString())
    }
}

internal inline fun <reified T : Any> T.logDebug(log: String) {
    if (isLoggingEnabled) {
        Timber.e(log)
    }
}