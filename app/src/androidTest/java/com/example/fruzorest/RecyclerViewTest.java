package com.example.fruzorest;


import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class RecyclerViewTest {

    private ViewProductAdminActivity viewProductAdminActivity;


    @Rule
    public ActivityTestRule<ViewProductAdminActivity> mActivityTestRule = new ActivityTestRule<ViewProductAdminActivity>(ViewProductAdminActivity.class);

    @Test
    public void recyclerViewTest() throws Exception {
        Espresso.onView(ViewMatchers.withId(R.id.pa_recycler)).perform(RecyclerViewActions.actionOnItemAtPosition(0, ViewActions.click()));
    }
}
