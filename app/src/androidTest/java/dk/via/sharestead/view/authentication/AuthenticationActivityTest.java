package dk.via.sharestead.view.authentication;

import android.os.SystemClock;

import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import dk.via.sharestead.R;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4ClassRunner.class)
public class AuthenticationActivityTest {
    @Rule
    public ActivityTestRule activityTestRule = new ActivityTestRule(AuthenticationActivity.class);

    @Test
    public void isActivityInView() {
        onView(withId(R.id.authenticationLayout)).check(matches(isDisplayed()));
    }

    @Test
    public void visibilityOfLoginComponents() {
        onView(withId(R.id.logoImage)).check(matches(isDisplayed()));
        onView(withId(R.id.logo)).check(matches(isDisplayed()));
        onView(withId(R.id.header)).check(matches(isDisplayed()));
        onView(withId(R.id.emailField)).check(matches(isDisplayed()));
        onView(withId(R.id.passwordField)).check(matches(isDisplayed()));
        onView(withId(R.id.forgotPassword)).check(matches(isDisplayed()));
        onView(withId(R.id.logInBtn)).check(matches(isDisplayed()));
        onView(withId(R.id.registerReference)).check(matches(isDisplayed()));
    }


    @Test
    public void testLoginButton() {
        onView(withId(R.id.emailField)).perform(replaceText("ledaodavid@gmail.com"));
        onView(withId(R.id.passwordField)).perform(replaceText("123456"));

        onView(withId(R.id.logInBtn)).perform(click());
        SystemClock.sleep(3000);

        onView(withId(R.id.authenticationLayout)).check(doesNotExist());
    }

    @Test
    public void testForgotPassword() {
        onView(withId(R.id.forgotPassword)).perform(click());
        onView(withText("Recover password")).check(matches(isDisplayed()));
    }

    @Test
    public void testForgotPasswordCancel(){
        onView(withId(R.id.forgotPassword)).perform(click());
        onView(withText("Cancel")).perform(click());
        onView(withText("Recover password")).check(doesNotExist());
    }

    @Test
    public void testRegisterOpening() {
        onView(withId(R.id.registerReference)).perform(click());
        onView(withId(R.id.registerLayout)).check(matches(isDisplayed()));
    }


}