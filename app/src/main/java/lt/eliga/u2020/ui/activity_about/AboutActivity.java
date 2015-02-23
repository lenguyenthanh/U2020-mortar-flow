package lt.eliga.u2020.ui.activity_about;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.gson.Gson;

import java.util.UUID;

import flow.Backstack;
import lt.eliga.u2020.R;
import lt.eliga.u2020.U2020Component;
import lt.eliga.u2020.U2020DataGraph;
import lt.eliga.u2020.core.navigation_activity.ActivityScreen;
import lt.eliga.u2020.core.navigation_screen.flow.FlowBundler;
import lt.eliga.u2020.core.util.GsonParceler;
import lt.eliga.u2020.core.ScopeSingleton;
import lt.eliga.u2020.ui._activity_base.U2020Activity;
import lt.eliga.u2020.ui.activity_about.about.AboutScreen;
import mortar.MortarScope;
import mortar.bundler.BundleServiceRunner;
import mortar.dagger2support.DaggerService;

import static mortar.MortarScope.buildChild;
import static mortar.dagger2support.DaggerService.createComponent;

public class AboutActivity extends U2020Activity {

    private final FlowBundler flowBundler = new FlowBundler(new GsonParceler(new Gson())) {
        @Override protected Backstack getColdStartBackstack(
                @Nullable Backstack restoredBackstack) {
            return restoredBackstack == null ? Backstack.single(
                    new AboutScreen()
            ) : restoredBackstack;
        }
    };
    private String scopeName;

    @ScopeSingleton(Component.class)
    @dagger.Component(
            dependencies = U2020Component.class
    )
    public interface Component extends U2020DataGraph {
        void inject(AboutActivity activity);
    }

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.gallery_activity_title);
    }

    @Override protected FlowBundler getFlowBundler() {
        return flowBundler;
    }

    @Override protected MortarScope createActivityScope(U2020Component appComponent) {
        return buildChild(getApplicationContext(), getScopeName())
                .withService(BundleServiceRunner.SERVICE_NAME, new BundleServiceRunner())
                .withService(
                        DaggerService.SERVICE_NAME, createComponent(
                                Component.class, appComponent
                        )
                )
                .build();
    }

    @Override protected void onCreateComponent() {
        DaggerService.<Component>getDaggerComponent(this).inject(this);
    }

    @Override protected String getScopeName() {
        if (scopeName == null) scopeName = (String) getLastCustomNonConfigurationInstance();
        if (scopeName == null) {
            scopeName = getClass().getName() + "-" + UUID.randomUUID().toString();
        }
        return scopeName;
    }

    @Override
    protected void onExtractParams(@NonNull Bundle params) {
        super.onExtractParams(params);
    }

    @Override protected void onDestroy() {
        super.onDestroy();
    }

    public static class Screen extends ActivityScreen {

        private static final String BF_IMAGE_ID = "ImgurImageActivity.imageId";

        private final String imageId;

        public Screen(String imageId) {
            this.imageId = imageId;
        }

        @Override
        protected void configureIntent(@NonNull Intent intent) {
            intent.putExtra(BF_IMAGE_ID, imageId);
        }

        @Override
        protected Class<? extends Activity> activityClass() {
            return AboutActivity.class;
        }
    }
}
