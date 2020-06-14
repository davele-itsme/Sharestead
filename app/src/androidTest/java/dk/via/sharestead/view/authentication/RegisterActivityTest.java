package dk.via.sharestead.view.authentication;

import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import dk.via.sharestead.R;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.*;

@RunWith(AndroidJUnit4ClassRunner.class)
public class RegisterActivityTest {
    @Rule
    public ActivityTestRule activityTestRule = new ActivityTestRule(RegisterActivity.class);

    @Test
    public void isActivityInView() {
        onView(withId(R.id.authenticationLayout)).check(matches(isDisplayed()));
    }

    @Test
    public void visibilityOfRegisterComponents() {
        onView(withId(R.id.logoImage)).check(matches(isDisplayed()));
        onView(withId(R.id.title)).check(matches(isDisplayed()));
        onView(withId(R.id.emailField)).check(matches(isDisplayed()));
        onView(withId(R.id.passwordField)).check(matches(isDisplayed()));
        onView(withId(R.id.repeatPasswordField)).check(matches(isDisplayed()));
        onView(withId(R.id.registerBtn)).check(matches(isDisplayed()));
        onView(withId(R.id.loginReference)).check(matches(isDisplayed()));
        onView(withId(R.id.userAgreement)).check(matches(isDisplayed()));
    }

    @Test
    public void testLoginOpening() {
        onView(withId(R.id.loginReference)).perform(click());
        onView(withId(R.id.authenticationLayout)).check(matches(isDisplayed()));
    }

    @Test
    public void testEmptyFieldsRegister()
    {
        onView(withId(R.id.registerBtn)).perform(click());
        onView(withId(R.id.registerLayout)).check(matches(isDisplayed()));
    }
}