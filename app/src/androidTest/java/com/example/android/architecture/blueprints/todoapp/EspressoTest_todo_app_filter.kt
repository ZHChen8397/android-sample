package com.example.android.architecture.blueprints.todoapp

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.android.architecture.blueprints.todoapp.tasks.TasksActivity
import org.hamcrest.Matchers.allOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TodoAppBasicFilter {
    @get:Rule
    var activityRule = ActivityScenarioRule(TasksActivity::class.java)
    private var testTitle = "Test_Title"
    private var testDescription = "Test_Description"
    private var testCompleteTitle = "Test_Complete_Title"
    private var testCompleteDescription = "Test_Complete_Description"
    private var testClickBack = "Navigate up"
    private var testFilterAll = "All"
    private var testFilterActive = "Active"
    private var testFilterCompleted = "Completed"

    private fun addTask(title: String, description: String){
        onView(withId(R.id.add_task_fab)).perform(click())
        onView(withId(R.id.add_task_title_edit_text)).perform(typeText(title))
        onView(withId(R.id.add_task_description_edit_text)).perform(typeText(description))
        onView(withId(R.id.save_task_fab)).perform(click())
    }

    // Filter
    @Test
    fun testFilterActiveTask(){
        addTask(testCompleteTitle, testCompleteDescription)
        onView(withId(R.id.tasks_list)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        onView(withId(R.id.task_detail_complete_checkbox)).perform(click())
        onView(withContentDescription(testClickBack)).perform(click())
        addTask(testTitle, testDescription)
        onView(withId(R.id.menu_filter)).perform(click())
        onView(allOf(withId(R.id.title), withText(testFilterActive))).perform(click())
        onView(withId(R.id.title_text)).check(matches(withText(testTitle)))
    }

    @Test
    fun testFilterCompleteTask(){
        addTask(testCompleteTitle, testCompleteDescription)
        onView(withId(R.id.tasks_list)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        onView(withId(R.id.task_detail_complete_checkbox)).perform(click())
        onView(withContentDescription(testClickBack)).perform(click())
        addTask(testTitle, testDescription)
        onView(withId(R.id.menu_filter)).perform(click())
        onView(allOf(withId(R.id.title), withText(testFilterCompleted))).perform(click())
        onView(withId(R.id.title_text)).check(matches(withText(testCompleteTitle)))
    }

    @Test
    fun testFilterCompletedThenFilterAllTask() {
        addTask(testCompleteTitle, testCompleteDescription)
        onView(withId(R.id.tasks_list)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        onView(withId(R.id.task_detail_complete_checkbox)).perform(click())
        onView(withContentDescription(testClickBack)).perform(click())
        addTask(testTitle, testDescription)
        onView(withId(R.id.menu_filter)).perform(click())
        onView(allOf(withId(R.id.title), withText(testFilterCompleted))).perform(click())
        onView(withId(R.id.menu_filter)).perform(click())
        onView(allOf(withId(R.id.title), withText(testFilterAll))).perform(click())
        onView(allOf(withId(R.id.tasks_list), hasDescendant(withText(testCompleteTitle)))).check(matches(isDisplayed()))
        onView(allOf(withId(R.id.tasks_list), hasDescendant(withText(testTitle)))).check(matches(isDisplayed()))
    }

    @Test
    fun testFilterActiveThenFilterAllTask() {
        addTask(testCompleteTitle, testCompleteDescription)
        onView(withId(R.id.tasks_list)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        onView(withId(R.id.task_detail_complete_checkbox)).perform(click())
        onView(withContentDescription(testClickBack)).perform(click())
        addTask(testTitle, testDescription)
        onView(withId(R.id.menu_filter)).perform(click())
        onView(allOf(withId(R.id.title), withText(testFilterActive))).perform(click())
        onView(withId(R.id.menu_filter)).perform(click())
        onView(allOf(withId(R.id.title), withText(testFilterAll))).perform(click())
        onView(allOf(withId(R.id.tasks_list), hasDescendant(withText(testCompleteTitle)))).check(matches(isDisplayed()))
        onView(allOf(withId(R.id.tasks_list), hasDescendant(withText(testTitle)))).check(matches(isDisplayed()))
    }
}