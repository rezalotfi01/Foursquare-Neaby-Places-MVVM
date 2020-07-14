package com.reza.mymvvm.util

import androidx.test.espresso.IdlingRegistry
import androidx.test.rule.ActivityTestRule
import org.junit.rules.TestWatcher
import org.junit.runner.Description

/**
 * A JUnit that registers an idling resource for all fragments
 */
class DataBindingIdlingResourceRule(
    activityTestRule: ActivityTestRule<*>
) : TestWatcher() {
    private val idlingResource = DataBindingIdlingResource(activityTestRule)

    override fun finished(description: Description?) {
        IdlingRegistry.getInstance().unregister(idlingResource)
        super.finished(description)
    }

    override fun starting(description: Description?) {
        IdlingRegistry.getInstance().register(idlingResource)
        super.starting(description)
    }

}