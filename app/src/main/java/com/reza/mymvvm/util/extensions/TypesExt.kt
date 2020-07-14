package com.reza.mymvvm.util.extensions

fun Int.distancePerUnit(): String =
    try {
        if (this > 1000)
            (this / 1000).toString() + " KM"
        else
            this.toString()
    } catch (e: Exception) {
        logDebug(e?.localizedMessage)
        this.toString()
    }
