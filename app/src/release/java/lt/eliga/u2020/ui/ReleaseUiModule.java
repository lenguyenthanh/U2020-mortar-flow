package lt.eliga.u2020.ui;

import dagger.Module;
import dagger.Provides;
import lt.eliga.u2020.core.ActivityHierarchyServer;

@Module(includes = UiModule.class)
public class ReleaseUiModule {
    @Provides
    @ApplicationScope
    AppContainer provideAppContainer() {
        return AppContainer.DEFAULT;
    }

    @Provides
    @ApplicationScope ActivityHierarchyServer provideActivityHierarchyServer() {
        return ActivityHierarchyServer.NONE;
    }
}
