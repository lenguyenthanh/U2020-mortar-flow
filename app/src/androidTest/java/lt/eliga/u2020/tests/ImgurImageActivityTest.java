package lt.eliga.u2020.tests;

import android.content.Intent;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import lt.eliga.u2020.R;
import lt.eliga.u2020.tests.util.ActivityRule;
import lt.eliga.u2020.tests.util.Constants;
import lt.eliga.u2020.tests.util.ViewActions;
import lt.eliga.u2020.ui.activity_main.image.ImgurImageActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isRoot;
import static android.support.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static junit.framework.Assert.assertNotNull;


@RunWith(AndroidJUnit4.class)
public class ImgurImageActivityTest {

    @Rule
    public final ActivityRule<ImgurImageActivity> main = new ActivityRule<ImgurImageActivity>(ImgurImageActivity.class)
    {
        @Override
        protected Intent getLaunchIntent(String targetPackage, Class activityClass) {
            Intent intent = super.getLaunchIntent(targetPackage, activityClass);
            intent.putExtra("ImgurImageActivity.imageId", "0y3uACw");
            return intent;
        }
    };

    private ImgurImageActivity activity;

    @Before
    public void setUp() {
        activity = main.get();
    }

    @After
    public void tearDown() {
        activity = null;
    }

    @Test
    public void verifyImage() {
        assertNotNull(activity);
        onView(isRoot()).perform(ViewActions.waitAtLeast(Constants.WAIT_DELAY));
        onView(withId(R.id.imgur_image_view)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
    }
}
