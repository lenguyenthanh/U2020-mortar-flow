package lt.eliga.u2020;

import android.app.Application;

import dagger.Module;
import dagger.Provides;
import lt.eliga.u2020.ui.ApplicationScope;

@Module
public final class U2020AppModule {
    private final U2020App app;

    public U2020AppModule(U2020App app) {
        this.app = app;
    }

    @Provides
    @ApplicationScope Application provideApplication() {
        return app;
    }
}
