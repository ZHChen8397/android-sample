package com.example.android.architecture.blueprints.todoapp

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.android.architecture.blueprints.todoapp.tasks.TasksActivity
import org.hamcrest.Matchers.not
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TodoAppCRUDUpdate {
    @get:Rule
    var activityRule = ActivityScenarioRule(TasksActivity::class.java)
    private var testTitle = "Test_Title"
    private var testDescription = "Test_Description"
    private val testCompleteTitle = "Test_Complete_Title"
    private val testCompleteDescription = "Test_Complete_Description"
    private var testEditTitle = "Test_Edit_Title"
    private var testEditDescription = "Test_Edit_Description"
    private var testClickBack = "Navigate up"

    private fun addTask(title: String, description: String){
        onView(withId(R.id.add_task_fab)).perform(click())
        onView(withId(R.id.add_task_title_edit_text)).perform(typeText(title))
        onView(withId(R.id.add_task_description_edit_text)).perform(typeText(description))
        onView(withId(R.id.save_task_fab)).perform(click())
    }

    // Update
    @Test
    fun testEditTitle() {
        addTask(testTitle, testDescription)
        onView(withId(R.id.tasks_list)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        onView(withId(R.id.edit_task_fab)).perform(click())
        onView(withId(R.id.add_task_title_edit_text)).perform(replaceText(testEditTitle))
        onView(withId(R.id.save_task_fab)).perform(click())
        onView(withId(R.id.tasks_list)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        onView(withId(R.id.task_detail_title_text)).check(matches(withText(testEditTitle)))
    }

    @Test
    fun testEditDescription() {
        addTask(testTitle, testDescription)
        onView(withId(R.id.tasks_list)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        onView(withId(R.id.edit_task_fab)).perform(click())
        onView(withId(R.id.add_task_description_edit_text)).perform(replaceText(testEditDescription))
        onView(withId(R.id.save_task_fab)).perform(click())
        onView(withId(R.id.tasks_list)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        onView(withId(R.id.task_detail_description_text)).check(matches(withText(testEditDescription)))
    }

    @Test
    fun testEditTitleDescription(){
        addTask(testTitle, testDescription)
        onView(withId(R.id.tasks_list)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        onView(withId(R.id.edit_task_fab)).perform(click())
        onView(withId(R.id.add_task_description_edit_text)).perform(replaceText(testEditDescription))
        onView(withId(R.id.add_task_title_edit_text)).perform(replaceText(testEditTitle))
        onView(withId(R.id.save_task_fab)).perform(click())
        onView(withId(R.id.tasks_list)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        onView(withId(R.id.task_detail_description_text)).check(matches(withText(testEditDescription)))
        onView(withId(R.id.task_detail_title_text)).check(matches(withText(testEditTitle)))
    }

    @Test
    fun testEditTitleAndPressBack() {
        addTask(testTitle, testDescription)
        onView(withId(R.id.tasks_list)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        onView(withId(R.id.edit_task_fab)).perform(click())
        onView(withId(R.id.add_task_title_edit_text)).perform(replaceText(testEditTitle))
        onView(withContentDescription(testClickBack)).perform(click())
        onView(withId(R.id.task_detail_title_text)).check(matches(withText(testTitle)))
    }

    @Test
    fun testEditDescriptionAndPressBack() {
        addTask(testTitle, testDescription)
        onView(withId(R.id.tasks_list)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        onView(withId(R.id.edit_task_fab)).perform(click())
        onView(withId(R.id.add_task_description_edit_text)).perform(replaceText(testEditDescription))
        onView(withContentDescription(testClickBack)).perform(click())
        onView(withId(R.id.task_detail_description_text)).check(matches(withText(testDescription)))
    }

    @Test
    fun testEditTitleDescriptionAndPressBack() {
        addTask(testTitle, testDescription)
        onView(withId(R.id.tasks_list)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        onView(withId(R.id.edit_task_fab)).perform(click())
        onView(withId(R.id.add_task_title_edit_text)).perform(replaceText(testEditTitle))
        onView(withId(R.id.add_task_description_edit_text)).perform(replaceText(testEditDescription))
        onView(withContentDescription(testClickBack)).perform(click())
        onView(withId(R.id.task_detail_title_text)).check(matches(withText(testTitle)))
        onView(withId(R.id.task_detail_description_text)).check(matches(withText(testDescription)))
    }

    @Test
    fun testMarkCompleteInDetail(){
        addTask(testCompleteTitle, testCompleteDescription)
        onView(withId(R.id.tasks_list)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        onView(withId(R.id.task_detail_complete_checkbox)).perform(click())
        onView(withContentDescription(testClickBack)).perform(click())
        onView(withId(R.id.complete_checkbox)).check(matches(isChecked()))
    }

    @Test
    fun testMarkCompleteTwiceInDetail(){
        addTask(testCompleteTitle, testCompleteDescription)
        onView(withId(R.id.tasks_list)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        onView(withId(R.id.task_detail_complete_checkbox)).perform(click())
        onView(withId(R.id.task_detail_complete_checkbox)).perform(click())
        onView(withContentDescription("Navigate up")).perform(click())
        onView(withId(R.id.complete_checkbox)).check(matches(not(isChecked())))
    }

    @Test
    fun testMarkCompleteByCheckBox(){
        addTask(testCompleteTitle, testCompleteDescription)
        onView(withId(R.id.complete_checkbox)).perform(click())
        onView(withId(R.id.complete_checkbox)).check(matches(isChecked()))
    }

    @Test
    fun testMarkCompleteTwiceByCheckBox(){
        addTask(testCompleteTitle, testCompleteDescription)
        onView(withId(R.id.complete_checkbox)).perform(click())
        onView(withId(R.id.complete_checkbox)).perform(click())
        onView(withId(R.id.complete_checkbox)).check(matches(not(isChecked())))
    }

    // need work
    // 1. create two tasks and check one from main UI
}