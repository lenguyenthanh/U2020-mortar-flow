package lt.eliga.u2020;

import android.app.Application;
import android.content.Context;

import javax.inject.Inject;

import mortar.MortarScope;
import mortar.dagger2support.DaggerService;
import lt.eliga.u2020.core.ActivityHierarchyServer;
import timber.log.Timber;

import static mortar.dagger2support.DaggerService.createComponent;
import static timber.log.Timber.DebugTree;

public class U2020App extends Application {

    private U2020Component component;
    public  MortarScope    applicationScope;

    @Inject
    ActivityHierarchyServer activityHierarchyServer;

    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG) {
            Timber.plant(new DebugTree());
        } else {
            // TODO Crashlytics.start(this);
            // TODO Timber.plant(new CrashlyticsTree());
        }

        buildComponentAndInject();

        registerActivityLifecycleCallbacks(activityHierarchyServer);
    }

    public void buildComponentAndInject() {


        component = U2020Component.Initializer.init(this);
        component.inject(this);
    }


    @Override public Object getSystemService(String name) {
        if (applicationScope == null) applicationScope = MortarScope.buildRootScope().withService(
                DaggerService.SERVICE_NAME, createComponent(
                        U2020Component.class, new U2020AppModule(this)
                )
        ).build();

        return applicationScope.hasService(name) ? applicationScope.getService(
                name
        ) : super.getSystemService(name);
    }

    public U2020Component component() {
        return component;
    }

    public static U2020App get(Context context) {
        return (U2020App) context.getApplicationContext();
    }
}
