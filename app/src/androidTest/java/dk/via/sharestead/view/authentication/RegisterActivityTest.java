package dk.via.sharestead.view.authentication;

import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import dk.via.sharestead.R;

import static androidx.test.espresso.Espresso.onView;
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
    public void visibilityOfLoginComponents() {
    
    }
}