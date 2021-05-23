package com.example.taskmaster;

import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;


import static androidx.test.espresso.Espresso.onView;


@RunWith(AndroidJUnit4.class)
public class MainActivityTest {


    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void titleTest() {
        ViewInteraction settingsButton = onView(
                allOf(withId(R.id.settings),
                        withParent(withParent(withId(android.R.id.content))),
                        isDisplayed()));
        settingsButton.perform(click());

        ViewInteraction usernameTest = onView(
                allOf(withId(R.id.username),
                        withParent(withParent(withId(android.R.id.content))),
                        isDisplayed()));
        usernameTest.perform(replaceText("Test"), closeSoftKeyboard());

        ViewInteraction saveButton = onView(
                allOf(withId(R.id.save),
                        isDisplayed()));
        saveButton.perform(click());

        ViewInteraction textView = onView(
        allOf(withId(R.id.username),
                withParent(withParent(withId(android.R.id.content))),
                isDisplayed()));
        textView.check(matches(withText("Test's Tasks")));
    }
}