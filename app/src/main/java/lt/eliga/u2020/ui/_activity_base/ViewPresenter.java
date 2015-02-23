package lt.eliga.u2020.ui._activity_base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;

public abstract class ViewPresenter<V extends View> {
    private V view = null;

    /**
     * Load has been called for the current {@link #view}.
     */
    private boolean loaded;

    public final void takeView(V view) {
        if (view == null) throw new NullPointerException("new view must not be null");

        if (this.view != view) {
            if (this.view != null) dropView(this.view);

            this.view = view;
            if (getView() != null && !loaded) {
                loaded = true;
                onLoad();
            }
        }
    }

    public void dropView(V view) {
        if (view == null) throw new NullPointerException("dropped view must not be null");
        if (view == this.view) {
            loaded = false;
            this.view = null;
            onDestroy();
        }
    }

    protected final V getView() {
        return view;
    }

    protected void onLoad() {
    }

    protected void onDestroy() {
    }

    protected void onRestore(@NonNull Bundle savedInstanceState) {
    }

    protected void onSave(@NonNull Bundle outState) {
    }
}
