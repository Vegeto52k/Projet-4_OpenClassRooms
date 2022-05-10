package fr.vegeto52.mareu;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasMinimumChildCount;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import android.content.Context;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

import static fr.vegeto52.mareu.utils.RecyclerViewItemCountAssertion.withItemCount;

import fr.vegeto52.mareu.databinding.ActivitySpinnerFilteredBinding;
import fr.vegeto52.mareu.ui.MainActivity;
import fr.vegeto52.mareu.utils.DeleteViewAction;
import fr.vegeto52.mareu.utils.ProfilView;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class MareuInstrumentedTest {

    private static int ITEMS_COUNT = 12;

    private ActivityScenario<MainActivity> mMainActivity;

    @Rule
    public ActivityScenarioRule<MainActivity> mActivityRule =
            new ActivityScenarioRule(MainActivity.class);

    @Before
    public void setUp() {
        mMainActivity = mActivityRule.getScenario();
    }

    @Test
    public void meetingList_shouldNotEmpty() {
        onView(withId(R.id.recyclerview)).check(matches(hasMinimumChildCount(1)));
    }

    @Test
    public void meetingList_deleteAction_shouldRemoveItem() {
        onView(withId(R.id.recyclerview)).check(withItemCount(ITEMS_COUNT));
        onView(withId(R.id.recyclerview)).perform(RecyclerViewActions.actionOnItemAtPosition(1, new DeleteViewAction()));
        onView(withId(R.id.recyclerview)).check(withItemCount(ITEMS_COUNT - 1));
    }

    @Test
    public void meetingItem_click_openDetailActivity() {
        onView(withId(R.id.recyclerview)).perform(RecyclerViewActions.actionOnItemAtPosition(1, new ProfilView()));
        onView(withId(R.id.detail_activity)).check(matches(isDisplayed()));
    }

    @Test
    public void meetingAdd_create() {
        onView(withId(R.id.recyclerview)).check(withItemCount(ITEMS_COUNT));
        onView(withId(R.id.add_meeting)).perform(click());
        onView(withId(R.id.edit_object)).perform(replaceText("Coucou"));
        onView(withId(R.id.edit_date)).perform(replaceText("12/12/2100"));
        onView(withId(R.id.edit_start_hour)).perform(replaceText("13:00"));
        onView(withId(R.id.edit_end_hour)).perform(replaceText("14:00"));
        onView(withId(R.id.edit_mail_people)).perform(replaceText("TonyStark@gmail.com"));
        onView(withId(R.id.add_button_meeting)).perform(click());
        onView(withId(R.id.recyclerview)).check(withItemCount(ITEMS_COUNT + 1));
    }
}