package lt.eliga.u2020.ui.activity_main;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.google.gson.Gson;

import java.util.UUID;

import flow.Backstack;
import mortar.MortarScope;
import mortar.bundler.BundleServiceRunner;
import mortar.dagger2support.DaggerService;
import lt.eliga.u2020.R;
import lt.eliga.u2020.U2020Component;
import lt.eliga.u2020.core.navigation_activity.NoParamsActivityScreen;
import lt.eliga.u2020.core.navigation_screen.flow.FlowBundler;
import lt.eliga.u2020.core.util.GsonParceler;
import lt.eliga.u2020.ui.ScopeSingleton;
import lt.eliga.u2020.ui._activity_base.U2020Activity;
import lt.eliga.u2020.ui.activity_main.gallery.GalleryScreen;

import static mortar.MortarScope.buildChild;
import static mortar.dagger2support.DaggerService.createComponent;

public class MainActivity extends U2020Activity {

    private final FlowBundler flowBundler = new FlowBundler(new GsonParceler(new Gson())) {
        @Override protected Backstack getColdStartBackstack(
                @Nullable Backstack restoredBackstack) {
            return restoredBackstack == null ? Backstack.single(
                    new GalleryScreen()
            ) : restoredBackstack;
        }
    };
    private String scopeName;

    @ScopeSingleton(ActivityComponent.class)
    @dagger.Component(
            dependencies = U2020Component.class
    )
    public interface ActivityComponent {
        void inject(MainActivity activity);
    }

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.gallery_activity_title);
    }

    @Override protected String getScopeName() {
        if (scopeName == null) scopeName = (String) getLastCustomNonConfigurationInstance();
        if (scopeName == null) {
            scopeName = getClass().getName() + "-" + UUID.randomUUID().toString();
        }
        return scopeName;
    }

    @Override protected void onCreateComponent() {
        DaggerService.<ActivityComponent>getDaggerComponent(this).inject(this);
    }

    @Override protected FlowBundler getFlowBundler() {
        return flowBundler;
    }


    @Override protected MortarScope createActivityScope(U2020Component appComponent) {

        return buildChild(getApplicationContext(), getScopeName())
                .withService(BundleServiceRunner.SERVICE_NAME, new BundleServiceRunner())
                .withService(
                        DaggerService.SERVICE_NAME, createComponent(
                                ActivityComponent.class, appComponent
                        )
                )
                .build();

    }

    public static class Screen extends NoParamsActivityScreen {
        @Override
        protected Class<? extends Activity> activityClass() {
            return MainActivity.class;
        }
    }
}
