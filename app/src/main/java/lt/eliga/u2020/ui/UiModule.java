package lt.eliga.u2020.ui;

import dagger.Module;
import dagger.Provides;
import lt.eliga.u2020.ApplicationScope;
import lt.eliga.u2020.core.navigation_activity.ActivityScreenSwitcher;
import lt.eliga.u2020.core.navigation_activity.ScreenSwitcher;
import lt.eliga.u2020.core.navigation_activity.ToolbarPresenter;

@Module
public class UiModule {

    @Provides
    @ApplicationScope ToolbarPresenter proviceToolbarPresenter() {
        return new ToolbarPresenter();
    }

    @Provides
    @ApplicationScope ActivityScreenSwitcher provideActivityScreenSwitcher() {
        return new ActivityScreenSwitcher();
    }

    @Provides
    @ApplicationScope ScreenSwitcher provideScreenSwitcher(
            ActivityScreenSwitcher activityScreenSwitcher) {
        return activityScreenSwitcher;
    }
}
