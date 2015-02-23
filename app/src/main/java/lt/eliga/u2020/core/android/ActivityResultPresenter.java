package lt.eliga.u2020.core.android;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;

import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Singleton;

import mortar.MortarScope;
import mortar.Presenter;
import mortar.Scoped;
import mortar.bundler.BundleService;

import static mortar.bundler.BundleService.getBundleService;

@Singleton
public class ActivityResultPresenter extends Presenter<ActivityResultPresenter.View>
        implements ActivityResultRegistrar {

    private final Set<Registration> registrations = new HashSet<>();

    public interface View {
        void startActivity(Intent intent);

        void startActivityForResult(int requestCode, Intent intent);

        Context getContext();
    }

    public interface ActivityResultListener {
        void onActivityResult(int requestCode, int resultCode, Intent data);
    }

    @Inject ActivityResultPresenter() {
    }

    @Override protected BundleService extractBundleService(View view) {
        return getBundleService(view.getContext());
    }


    @Override public void startActivityForResult(int requestCode, Intent intent) {
        if (getView() == null) return;
        getView().startActivityForResult(requestCode, intent);
    }

    @Override public void onExitScope() {
        registrations.clear();
    }

    @Override public void register(MortarScope scope, ActivityResultListener listener) {
        Registration registration = new Registration(listener);
        scope.register(registration);
        registrations.add(registration);
    }

    public void startActivity(@NonNull Intent intent) {
        if (getView() == null) return;
        getView().startActivity(intent);
    }

    public void onActivityResultReceived(int requestCode, int resultCode, Intent data) {
        for (Registration registration : registrations) {
            registration.registrant.onActivityResult(requestCode, resultCode, data);
        }
    }

    private class Registration implements Scoped {
        final ActivityResultListener registrant;

        private Registration(ActivityResultListener registrant) {
            this.registrant = registrant;
        }

        @Override public void onEnterScope(MortarScope scope) {
        }

        @Override public void onExitScope() {
            registrations.remove(this);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Registration that = (Registration) o;

            return registrant.equals(that.registrant);
        }

        @Override
        public int hashCode() {
            return registrant.hashCode();
        }
    }
}

