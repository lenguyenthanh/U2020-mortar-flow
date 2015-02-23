package lt.eliga.u2020.ui.activity_about.about;

import android.app.Application;
import android.os.Bundle;

import javax.inject.Inject;

import flow.Layout;
import flow.Path;
import mortar.MortarScope;
import mortar.ViewPresenter;
import lt.eliga.u2020.R;
import lt.eliga.u2020.ui.ScopeSingleton;
import lt.eliga.u2020.ui.WithComponent;
import lt.eliga.u2020.ui.activity_about.AboutComponent;

/**
 * Created by eliga on 14/02/15.
 */

@Layout(R.layout.about_view) @WithComponent(AboutComponent.class)
public class AboutScreen extends Path {

    @Inject Application app;


    @ScopeSingleton(AboutComponent.class)
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
