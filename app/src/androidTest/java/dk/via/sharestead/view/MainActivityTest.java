package dk.via.sharestead.view;

import android.os.SystemClock;

import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import dk.via.sharestead.R;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.longClick;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;


@RunWith(AndroidJUnit4ClassRunner.class)
public class MainActivityTest {
    @Rule
    public ActivityTestRule activityTestRule = new ActivityTestRule(MainActivity.class);

    @Test
    public void isActivityInView() {
        onView(withId(R.id.content)).check(matches(isDisplayed()));
    }

    @Test
    public void isHomeFragmentInView() {
        onView(withId(R.id.progressBar)).check(matches(isDisplayed()));
        SystemClock.sleep(3000);
        onView(withId(R.id.homeFragment)).check(matches(isDisplayed()));
    }

    @Test
    public void visibilityOfHomeComponents(){
        onView(withId(R.id.progressBar)).check(matches(isDisplayed()));
        SystemClock.sleep(3000);
        onView(withId(R.id.imgHomeTitle)).check(matches(isDisplayed()));
        onView(withId(R.id.welcome1)).check(matches(isDisplayed()));
        onView(withId(R.id.welcome2)).check(matches(isDisplayed()));
        onView(withId(R.id.welcome3)).check(matches(isDisplayed()));
        onView(withId(R.id.game1Image)).check(matches(isDisplayed()));
        onView(withId(R.id.game1Name)).check(matches(isDisplayed()));
//        Cannot scroll down to check more components
//        onView(withId(R.id.scrollableView))
//                .perform(swipeUp());
        onView(withId(R.id.bestGamesTitle)).check(matches(isDisplayed()));
        onView(withId(R.id.gridRecyclerView)).check(matches(isDisplayed()));
        onView(withId(R.id.bestUpcomingTitle)).perform(scrollTo());
        onView(withId(R.id.bestUpcomingTitle)).check(matches(isDisplayed()));
        onView(withId(R.id.horizontalRecyclerView)).perform(scrollTo());
        onView(withId(R.id.horizontalRecyclerView)).check(matches(isDisplayed()));
    }

    @Test
    public void testOnClickListener(){
        onView(withId(R.id.progressBar)).check(matches(isDisplayed()));
        SystemClock.sleep(3000);
        onView(withId(R.id.game1Image)).perform(click());
        onView(withId(R.id.gameDetailsLayout)).check(matches(isDisplayed()));
    }

    @Test
    public void testNotesFragment(){
        onView(withId(R.id.progressBar)).check(matches(isDisplayed()));
        SystemClock.sleep(3000);
        onView(withId(R.id.actionNotes)).perform(click());
        onView(withId(R.id.noteFragment)).check(matches(isDisplayed()));
        onView(withId(R.id.fab)).check(matches(isDisplayed()));
        onView(withId(R.id.noteRecyclerView)).check(matches(isDisplayed()));
    }

    @Test
    public void testAddNote(){
        onView(withId(R.id.progressBar)).check(matches(isDisplayed()));
        SystemClock.sleep(3000);
        onView(withId(R.id.actionNotes)).perform(click());
        onView(withId(R.id.noteFragment)).check(matches(isDisplayed()));
        onView(withId(R.id.fab)).perform(click());

        onView(withId(R.id.noteTitle)).perform(replaceText("TEST"));
        onView(withId(R.id.noteDescription)).perform(replaceText("TEST DESCRIPTION"));
        onView(withId(R.id.notePriority)).perform(replaceText("5"));
        onView(withId(R.id.saveChangesBtn)).perform(click());
        onView(withText("TEST")).check(matches(isDisplayed()));
    }

    @Test
    public void testUpdateNote(){
        onView(withId(R.id.progressBar)).check(matches(isDisplayed()));
        SystemClock.sleep(3000);
        onView(withId(R.id.actionNotes)).perform(click());
        onView(withId(R.id.noteFragment)).check(matches(isDisplayed()));
        onView(withText("TEST")).perform(click());

        onView(withId(R.id.noteTitle)).perform(replaceText("TEST UPDATED"));
        onView(withId(R.id.noteDescription)).perform(replaceText("TEST UPDATED DESCRIPTION"));
        onView(withId(R.id.notePriority)).perform(replaceText("6"));
        onView(withId(R.id.saveChangesBtn)).perform(click());
        onView(withText("TEST UPDATED")).check(matches(isDisplayed()));
    }

    @Test
    public void testDeleteNote(){
        onView(withId(R.id.progressBar)).check(matches(isDisplayed()));
        SystemClock.sleep(3000);
        onView(withId(R.id.actionNotes)).perform(click());
        onView(withId(R.id.noteFragment)).check(matches(isDisplayed()));
        onView(withText("TEST UPDATED")).perform(longClick());
        onView(withText("OKAY")).perform(longClick());
        onView(withText("TEST UPDATED")).check(doesNotExist());
    }

    @Test
    public void testProfileFragment(){
        onView(withId(R.id.progressBar)).check(matches(isDisplayed()));
        SystemClock.sleep(3000);
        onView(withId(R.id.actionProfile)).perform(click());
        onView(withId(R.id.profileFragment)).check(matches(isDisplayed()));
        onView(withId(R.id.profileImage)).check(matches(isDisplayed()));
        onView(withId(R.id.profileName)).check(matches(isDisplayed()));
        onView(withId(R.id.profileEmail)).check(matches(isDisplayed()));
        onView(withId(R.id.help)).check(matches(isDisplayed()));
        onView(withId(R.id.privacyPolicy)).check(matches(isDisplayed()));
    }

    @Test
    public void testPrivacyPolicy(){
        onView(withId(R.id.progressBar)).check(matches(isDisplayed()));
        SystemClock.sleep(3000);
        onView(withId(R.id.actionProfile)).perform(click());
        onView(withId(R.id.profileFragment)).check(matches(isDisplayed()));

        onView(withId(R.id.privacyPolicy)).perform(click());
        onView(withText("OKAY")).check(matches(isDisplayed()));
    }

    @Test
    public void testHelp(){
        onView(withId(R.id.progressBar)).check(matches(isDisplayed()));
        SystemClock.sleep(3000);
        onView(withId(R.id.actionProfile)).perform(click());
        onView(withId(R.id.profileFragment)).check(matches(isDisplayed()));

        onView(withId(R.id.help)).perform(click());
        onView(withText("UNDERSTAND")).check(matches(isDisplayed()));
    }



}