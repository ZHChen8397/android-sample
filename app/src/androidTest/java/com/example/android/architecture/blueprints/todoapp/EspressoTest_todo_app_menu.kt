package com.example.android.architecture.blueprints.todoapp

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.DrawerActions
import androidx.test.espresso.contrib.NavigationViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.android.architecture.blueprints.todoapp.tasks.TasksActivity
import org.hamcrest.Matchers.allOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TodoAppBasicMenu {
    @get:Rule
    var activityRule = ActivityScenarioRule(TasksActivity::class.java)
    private var testTitle = "Test_Title"
    private var testDescription = "Test_Description"
    private var testStatePlaceholder = "You have no tasks."
    private var testCompleteTitle = "Test_Complete_Title"
    private var testCompleteDescription = "Test_Complete_Description"

    private fun addTask(title: String, description: String){
        onView(withId(R.id.add_task_fab)).perform(click())
        onView(withId(R.id.add_task_title_edit_text)).perform(typeText(title))
        onView(withId(R.id.add_task_description_edit_text)).perform(typeText(description))
        onView(withId(R.id.save_task_fab)).perform(click())
    }

    // Menu
    @Test
    fun testClickStatistics() {
        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open())
        onView(withId(R.id.nav_view)).perform(NavigationViewActions.navigateTo(R.id.statistics_fragment_dest))
        onView(withId(R.id.stats_placeholder)).check(matches(withText(testStatePlaceholder)))
    }

    @Test
    fun testClickStatisticsWithOneActiveTask(){
        addTask(testTitle, testDescription)
        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open())
        onView(withId(R.id.nav_view)).perform(NavigationViewActions.navigateTo(R.id.statistics_fragment_dest))
        onView(withId(R.id.stats_active_text)).check(matches(isDisplayed()))
    }

    @Test
    fun testClickStatisticsWithOneActiveTaskOneCompleteTask(){
        addTask(testCompleteTitle, testCompleteDescription)
        addTask(testTitle, testDescription)
        onView(allOf(withId(R.id.complete_checkbox), withParent(hasDescendant(withText(testCompleteTitle))))).perform(click())
        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open())
        onView(withId(R.id.nav_view)).perform(NavigationViewActions.navigateTo(R.id.statistics_fragment_dest))
        onView(withId(R.id.stats_active_text)).check(matches(isDisplayed()))
        onView(withId(R.id.stats_completed_text)).check(matches(isDisplayed()))
    }

    @Test
    fun testClickStatisticsAndClickTaskLists() {
        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open())
        onView(withId(R.id.nav_view)).perform(NavigationViewActions.navigateTo(R.id.statistics_fragment_dest))
        onView(withId(R.id.stats_placeholder)).check(matches(withText(testStatePlaceholder)))
        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open())
        onView(withId(R.id.nav_view)).perform(NavigationViewActions.navigateTo(R.id.tasks_fragment_dest))
        onView(withId(R.id.no_tasks_icon)).check(matches(isDisplayed()))
    }
}