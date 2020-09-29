package com.example.android.architecture.blueprints.todoapp

import androidx.test.espresso.Espresso.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.android.architecture.blueprints.todoapp.tasks.TasksActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TodoAppBasicUI {
    @get:Rule
    var activityRule = ActivityScenarioRule(TasksActivity::class.java)

    // Basic UI
    @Test
    fun testShowNoTaskIcon(){
        onView(withId(R.id.no_tasks_icon)).check(matches(isDisplayed()))
    }

    @Test
    fun testShowNoTaskText(){
        onView(withId(R.id.no_tasks_text)).check(matches(isDisplayed()))
    }
}