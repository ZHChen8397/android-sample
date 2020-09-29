package com.example.android.architecture.blueprints.todoapp

import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.android.architecture.blueprints.todoapp.tasks.TasksActivity
import org.hamcrest.Matchers.hasToString
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TodoAppMoreOptions {
    @get:Rule
    var activityRule = ActivityScenarioRule(TasksActivity::class.java)
    private var testTitle = "Test_Title"
    private var testDescription = "Test_Description"
    private var testMoreOptions = "More options"
    private var testClearCompleted = "Clear completed"
    private var testRefresh = "Refresh"

    private fun addTask(title: String, description: String){
        onView(withId(R.id.add_task_fab)).perform(click())
        onView(withId(R.id.add_task_title_edit_text)).perform(typeText(title))
        onView(withId(R.id.add_task_description_edit_text)).perform(typeText(description))
        onView(withId(R.id.save_task_fab)).perform(click())
    }

    // More Options
    @Test
    fun testClearCompleteTask(){
        addTask(testTitle, testDescription)
        onView(withId(R.id.complete_checkbox)).perform(click())
        onView(withId(R.id.complete_checkbox)).check(matches(isChecked()))
        onView(withContentDescription(testMoreOptions)).perform(click())
        onData(hasToString(testClearCompleted)).perform(click())
        onView(withId(R.id.no_tasks_text)).check(matches(isDisplayed()))
    }

    @Test
    fun testRefreshTask(){
        addTask(testTitle, testDescription)
        onView(withContentDescription(testMoreOptions)).perform(click())
        onData(hasToString(testRefresh)).perform(click())
        onView(withId(R.id.title_text)).check(matches(withText(testTitle)))
    }
}