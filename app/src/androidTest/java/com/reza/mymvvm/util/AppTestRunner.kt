package com.reza.mymvvm.util

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner
import com.reza.mymvvm.TestApp

/**
 * Custom runner disabling dependency injection.
 */
class AppTestRunner : AndroidJUnitRunner() {
    override fun newApplication(cl: ClassLoader, className: String, context: Context): Application {
        return super.newApplication(cl, TestApp::class.java.name, context)
    }
}
