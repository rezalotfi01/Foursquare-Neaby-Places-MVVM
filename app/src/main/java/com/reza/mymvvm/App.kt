package com.reza.mymvvm

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.multidex.MultiDexApplication
import coil.Coil
import coil.ImageLoader
import coil.util.CoilUtils
import com.facebook.stetho.Stetho
import com.reza.mymvvm.di.Injectable
import com.reza.mymvvm.di.component.DaggerAppComponent
import com.reza.mymvvm.util.timber.CrashReportingTree
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import dagger.android.support.AndroidSupportInjection
import okhttp3.OkHttpClient
import timber.log.Timber
import javax.inject.Inject

class App : MultiDexApplication(), HasAndroidInjector {
    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>

    override fun onCreate() {
        super.onCreate()

        initStetho()
        initImageLoader()
        initLogTimber()

        initDI()
    }

    override fun androidInjector(): AndroidInjector<Any> = dispatchingAndroidInjector

    private fun initStetho() {
        if (BuildConfig.DEBUG) Stetho.initializeWithDefaults(this)
    }

    private fun initImageLoader(){
        val imageLoader = ImageLoader.Builder(applicationContext)
            .okHttpClient {
                OkHttpClient.Builder()
                    .cache(CoilUtils.createDefaultCache(applicationContext))
                    .build()
            }
            .build()
        Coil.setImageLoader(imageLoader)
    }

    private fun initLogTimber() {
        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
        else Timber.plant(CrashReportingTree())
    }

    private fun initDI() {
        DaggerAppComponent
            .factory()
            .create(this)
            .inject(this)   

        registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacks {
            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                checkActivityInjection(activity)
            }

            override fun onActivityStarted(activity: Activity) {

            }
            override fun onActivityResumed(activity: Activity) {

            }
            override fun onActivityPaused(activity: Activity) {

            }
            override fun onActivityStopped(activity: Activity) {

            }
            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle?) {

            }
            override fun onActivityDestroyed(activity: Activity) {

            }
        })
    }

    private fun checkActivityInjection(activity: Activity) {
        if (activity is HasAndroidInjector) {
            AndroidInjection.inject(activity)
        }
        if (activity is FragmentActivity) {
            activity.supportFragmentManager
                .registerFragmentLifecycleCallbacks(
                    object : FragmentManager.FragmentLifecycleCallbacks() {
                        override fun onFragmentCreated(
                            fm: FragmentManager,
                            f: Fragment,
                            savedInstanceState: Bundle?
                        ) {
                            if (f is Injectable) {
                                AndroidSupportInjection.inject(f)
                            }
                        }
                    }, true
                )
        }
    }
}