package lt.eliga.u2020;

import dagger.Component;
import lt.eliga.u2020.data.DebugDataModule;
import lt.eliga.u2020.ui.ApplicationScope;
import lt.eliga.u2020.ui.DebugUiModule;

/**
* The core debug component for u2020 applications
*/
@ApplicationScope
@Component(modules = { U2020AppModule.class, DebugUiModule.class, DebugDataModule.class })
public interface U2020Component extends DebugU2020Graph {
    /**
     * An initializer that creates the graph from an application.
     */
    final static class Initializer {
        static U2020Component init(U2020App app) {
            return Dagger_U2020Component.builder()
                    .u2020AppModule(new U2020AppModule(app))
                    .build();
        }
        private Initializer() {} // No instances.
    }
}

