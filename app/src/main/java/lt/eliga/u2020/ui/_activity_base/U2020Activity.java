package lt.eliga.u2020.ui._activity_base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import javax.inject.Inject;

import butterknife.ButterKnife;
import flow.Flow;
import flow.HasParent;
import flow.Path;
import flow.PathContainerView;
import mortar.MortarScope;
import mortar.bundler.BundleServiceRunner;
import lt.eliga.u2020.R;
import lt.eliga.u2020.U2020App;
import lt.eliga.u2020.U2020Component;
import lt.eliga.u2020.core.navigation_activity.ActivityScreenSwitcher;
import lt.eliga.u2020.core.navigation_activity.ToolbarPresenter;
import lt.eliga.u2020.core.navigation_screen.flow.FlowBundler;
import lt.eliga.u2020.core.navigation_screen.flow.HandlesBack;
import lt.eliga.u2020.core.navigation_screen.flow.HandlesUp;
import lt.eliga.u2020.ui.AppContainer;

import static android.content.Intent.ACTION_MAIN;
import static android.content.Intent.CATEGORY_LAUNCHER;
import static mortar.MortarScope.findChild;

public abstract class U2020Activity extends ActionBarActivity implements Flow.Dispatcher {

    @Inject AppContainer           appContainer;
    @Inject ActivityScreenSwitcher activityScreenSwitcher;
    @Inject ToolbarPresenter       toolbarPresenter;
    private Toolbar                toolbar;
    private Flow                   flow;
    private PathContainerView      contentContainer;
    private HandlesBack            containerAsHandlesBack;
    private HandlesUp              containerAsHandlesUp;
    private boolean                configurationChangeIncoming;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Bundle params = getIntent().getExtras();
        if (params != null) {
            onExtractParams(params);
        }
        super.onCreate(savedInstanceState);

        if (isWrongInstance()) {
            finish();
            return;
        }

        BundleServiceRunner.getBundleServiceRunner(this).onCreate(savedInstanceState);

        onCreateComponent();

        flow = getFlowBundler().onCreate(savedInstanceState);

        final LayoutInflater layoutInflater = getLayoutInflater();

        ViewGroup mainContainer = appContainer.get(this);
        layoutInflater.inflate(R.layout.toolbar_container_view, mainContainer);

        toolbar = ButterKnife.findById(this, R.id.top_activity_toolbar);
        setSupportActionBar(toolbar);

        contentContainer = (PathContainerView) ButterKnife.findById(this, R.id.content_container);

        containerAsHandlesBack = (HandlesBack) contentContainer;
        containerAsHandlesUp = (HandlesUp) contentContainer;
    }

    @Override public void dispatch(Flow.Traversal traversal, Flow.TraversalCallback callback) {
        Path newScreen = traversal.destination.current();
        boolean hasUp = newScreen instanceof HasParent;
        String title = newScreen.getClass().getSimpleName();
//        ActionBarPresenter.MenuAction menu =
//                hasUp ? null : new ActionBarPresenter.MenuAction(
//                        "Friends", new Action0() {
//                    @Override public void call() {
//                        flow.goTo(new GalleryScreen());
//                    }
//                }
//                );
        contentContainer.dispatch(traversal, callback);
    }


    @Override
    protected void onStart() {
        super.onStart();
        activityScreenSwitcher.attach(this);
        toolbarPresenter.attach(toolbar);
    }

    @Override
    protected void onStop() {
        activityScreenSwitcher.detach();
        toolbarPresenter.detach();
        super.onStop();
    }

    @Override protected void onResume() {
        super.onResume();
        flow.setDispatcher(this);
    }

    @Override protected void onPause() {
        flow.removeDispatcher(this);
        super.onPause();
    }

    @Override public Object getSystemService(String name) {

        if (Flow.isFlowSystemService(name)) {
            return flow;
        }
        MortarScope activityScope = findChild(getApplicationContext(), getScopeName());

        if (activityScope == null) {
            activityScope = createActivityScope(U2020App.get(this).component());
        }

        return activityScope.hasService(name) ? activityScope.getService(name)
                : super.getSystemService(name);
    }

    @Override protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        getFlowBundler().onSaveInstanceState(outState);
        BundleServiceRunner.getBundleServiceRunner(this).
                onSaveInstanceState(outState);
    }


    @Override public void onBackPressed() {
        if (!containerAsHandlesBack.onBackPressed()) super.onBackPressed();
    }

    @Override protected void onDestroy() {
        // activityScope may be null in case isWrongInstance() returned true in onCreate()
        if (isFinishing()) {
            MortarScope activityScope = findChild(getApplicationContext(), getScopeName());
            if (activityScope != null && !activityScope.isDestroyed() &&
                    !configurationChangeIncoming)
                activityScope.destroy();
        }

        super.onDestroy();
    }

    @Override public Object onRetainCustomNonConfigurationInstance() {
        configurationChangeIncoming = true;
        return getScopeName();
    }


    private boolean isWrongInstance() {
        if (!isTaskRoot()) {
            Intent intent = getIntent();
            boolean isMainAction =
                    intent.getAction() != null && intent.getAction().equals(ACTION_MAIN);
            return intent.hasCategory(CATEGORY_LAUNCHER) && isMainAction;
        }
        return false;
    }

    protected void onExtractParams(@NonNull Bundle params) {
        // default no implemetation
    }

    protected abstract void onCreateComponent();

    protected abstract FlowBundler getFlowBundler();

    protected abstract MortarScope createActivityScope(U2020Component appComponent);

    protected abstract String getScopeName();
}
