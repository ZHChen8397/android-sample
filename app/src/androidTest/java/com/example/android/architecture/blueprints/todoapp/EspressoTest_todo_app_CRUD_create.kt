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
import org.hamcrest.CoreMatchers.allOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.lang.RuntimeException
import java.util.logging.Logger

@RunWith(AndroidJUnit4::class)
class TodoAppCRUDCreate {
    @get:Rule
    var activityRule = ActivityScenarioRule(TasksActivity::class.java)
    private var testTitle = "Test_Title"
    private var testDescription = "Test_Description"
    private var testClickBack = "Navigate up"
    private var testDescriptionPlaceholder = "Enter your task here."
    private var testTitlePlaceholder = "Title"

    private fun addMultiTask(num: Int){
        var title = ""
        var description = ""
        for(i in 1..num){
            title = "Title $i"
            description = "Description $i"
            onView(withId(R.id.add_task_fab)).perform(click())
            onView(withId(R.id.add_task_title_edit_text)).perform(typeText(title))
            onView(withId(R.id.add_task_description_edit_text)).perform(typeText(description))
            onView(withId(R.id.save_task_fab)).perform(click())
        }
    }

    private fun addTask(title: String, description: String) {
        onView(withId(R.id.add_task_fab)).perform(click())
        onView(withId(R.id.add_task_title_edit_text)).perform(typeText(title))
        onView(withId(R.id.add_task_description_edit_text)).perform(typeText(description))
        onView(withId(R.id.save_task_fab)).perform(click())
    }

    // Create
    @Test
    fun testAddTask() {
        addTask(testTitle, testDescription)
        onView(withId(R.id.title_text)).check(matches(withText(testTitle)))
    }

    @Test
    fun testAddTaskWithSymbolTiTleDescription(){
        addTask("#$%^&*()_+", ")(&^$#@")
        onView(withId(R.id.title_text)).check(matches(withText("#$%^&*()_+")))
    }

    @Test
    fun testAddTaskWithNoTitleDescription() {
        onView(withId(R.id.add_task_fab)).perform(click())
        onView(withId(R.id.save_task_fab)).perform(click())
        onView(withId(R.id.add_task_title_edit_text)).check(matches(withHint(testTitlePlaceholder)))
        onView(withId(R.id.add_task_description_edit_text)).check(matches(withHint(testDescriptionPlaceholder)))
    }

    @Test
    fun testAddTaskWithTitleOnly() {
        onView(withId(R.id.add_task_fab)).perform(click())
        onView(withId(R.id.add_task_title_edit_text)).perform(typeText(testTitle))
        onView(withId(R.id.save_task_fab)).perform(click())
        onView(withId(R.id.add_task_title_edit_text)).check(matches(withText(testTitle)))
        onView(withId(R.id.add_task_description_edit_text)).check(matches(withHint(testDescriptionPlaceholder)))
    }

    @Test
    fun testAddTaskWithDescriptionOnly() {
        onView(withId(R.id.add_task_fab)).perform(click())
        onView(withId(R.id.add_task_description_edit_text)).perform(typeText(testDescription))
        onView(withId(R.id.save_task_fab)).perform(click())
        onView(withId(R.id.add_task_description_edit_text)).check(matches(withText(testDescription)))
        onView(withId(R.id.add_task_title_edit_text)).check(matches(withHint(testTitlePlaceholder)))
    }

    @Test
    fun testAddTaskAndPressBack() {
        onView(withId(R.id.add_task_fab)).perform(click())
        Thread.sleep(1000)
        onView(withContentDescription(testClickBack)).perform(click())
        onView(withId(R.id.no_tasks_icon)).check(matches(isDisplayed()))
    }

    @Test
    fun testAddTaskWithTitleAndPressBack() {
        onView(withId(R.id.add_task_fab)).perform(click())
        onView(withId(R.id.add_task_title_edit_text)).perform(typeText(testTitle))
        Thread.sleep(1000)
        onView(withContentDescription(testClickBack)).perform(click())
        onView(withId(R.id.no_tasks_icon)).check(matches(isDisplayed()))
    }

    @Test
    fun testAddTaskWithDescriptionAndPressBack() {
        onView(withId(R.id.add_task_fab)).perform(click())
        onView(withId(R.id.add_task_description_edit_text)).perform(typeText(testDescription))
        Thread.sleep(1000)
        onView(withContentDescription(testClickBack)).perform(click())
        onView(withId(R.id.no_tasks_icon)).check(matches(isDisplayed()))
    }

    @Test
    fun testAddTaskWithTitleDescriptionAndPressBack() {
        onView(withId(R.id.add_task_fab)).perform(click())
        onView(withId(R.id.add_task_title_edit_text)).perform(typeText(testTitle))
        onView(withId(R.id.add_task_description_edit_text)).perform(typeText(testDescription))
        Thread.sleep(1000)
        onView(withContentDescription(testClickBack)).perform(click())
        onView(withId(R.id.no_tasks_icon)).check(matches(isDisplayed()))
    }

    @Test
    fun testAddTaskOutOfScreen(){
        addMultiTask(10)
        onView(withId(R.id.tasks_list)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(9))
        onView(allOf(withId(R.id.tasks_list), hasDescendant(withText("Title 10")))).check(matches(isDisplayed()))
    }
    // not find way to implement
    // 1. create task to max amount
    // 2. create task title to max character
    // 3. create task description to max description
}