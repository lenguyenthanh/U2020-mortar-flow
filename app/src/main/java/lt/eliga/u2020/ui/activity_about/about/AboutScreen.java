package lt.eliga.u2020.ui.activity_about.about;

import android.app.Application;
import android.os.Bundle;

import javax.inject.Inject;

import flow.Layout;
import flow.Path;
import lt.eliga.u2020.R;
import lt.eliga.u2020.core.ScopeSingleton;
import lt.eliga.u2020.core.WithComponent;
import lt.eliga.u2020.ui.activity_about.AboutActivity;
import mortar.MortarScope;
import mortar.ViewPresenter;

/**
 * Created by eliga on 14/02/15.
 */

@Layout(R.layout.about_view) @WithComponent(AboutScreen.Component.class)
public class AboutScreen extends Path {

    @Inject Application app;

    @dagger.Component(
            dependencies = AboutActivity.Component.class
    )
    @ScopeSingleton(Component.class)
    public interface Component {
        void inject(AboutView view);
    }

    @ScopeSingleton(Component.class)
    public static class Presenter extends ViewPresenter<AboutView> {

        @Inject Presenter() {}

        @Override public void onLoad(Bundle savedInstanceState) {
            super.onLoad(savedInstanceState);
        }

        @Override public void onEnterScope(MortarScope scope) {
            super.onEnterScope(scope);
        }

        @Override public void dropView(AboutView view) {
            super.dropView(view);
        }
    }
}
