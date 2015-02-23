package lt.eliga.u2020.ui;

import dagger.Module;
import dagger.Provides;
import lt.eliga.u2020.ui.debug.DebugAppContainer;
import lt.eliga.u2020.ui.debug.SocketActivityHierarchyServer;

@Module(includes = UiModule.class)
public class DebugUiModule {

    @Provides
    @ApplicationScope
    AppContainer provideAppContainer(DebugAppContainer debugAppContainer) {
        return debugAppContainer;
    }

    @Provides
    @ApplicationScope
    ActivityHierarchyServer provideActivityHierarchyServer() {
        return new SocketActivityHierarchyServer();
    }
}
