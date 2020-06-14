package dk.via.sharestead.view.preference;

import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import dk.via.sharestead.R;
import dk.via.sharestead.view.authentication.AuthenticationActivity;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.*;

@RunWith(AndroidJUnit4ClassRunner.class)
public class PreferenceActivityTest {
    @Rule
    public ActivityTestRule activityTestRule = new ActivityTestRule(PreferenceActivity.class);

    @Test
    public void isActivityInView() {
        onView(withId(R.id.preferenceLayout)).check(matches(isDisplayed()));
    }

    @Test
    public void visibilityOfLoginComponents() {
        onView(withId(R.id.welcome1)).check(matches(isDisplayed()));
        onView(withId(R.id.welcome2)).check(matches(isDisplayed()));
        onView(withId(R.id.platformSlider)).check(matches(isDisplayed()));
        onView(withId(R.id.continueButton)).check(matches(isDisplayed()));
    }

    @Test
    public void testContinueButton(){
        onView(withId(R.id.continueButton)).perform(click());
        onView(withText("Preference error")).check(matches(isDisplayed()));
        onView(withText("OKAY")).perform(click());
        onView(withId(R.id.preferenceLayout)).check(matches(isDisplayed()));
    }
}