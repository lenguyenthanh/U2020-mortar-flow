package lt.eliga.u2020.core.navigation_activity;

import android.support.annotation.IdRes;
import android.support.annotation.StringRes;
import android.support.v7.widget.Toolbar;

public class ToolbarPresenter extends ActivityConnector<Toolbar>{

    public void setMenuItemVisibility(@IdRes int id, boolean visible) {
        Toolbar currentToolbar = getAttachedObject();
        if (currentToolbar == null) {
            return;
        }
        currentToolbar.getMenu().findItem(id).setVisible(visible);
    }

    public void setTitle(@StringRes int resId) {
        Toolbar currentToolbar = getAttachedObject();
        if (currentToolbar == null) {
            return;
        }
        currentToolbar.setTitle(resId);
    }

    public void setTitle(String title) {
        Toolbar currentToolbar = getAttachedObject();
        if (currentToolbar == null) {
            return;
        }
        currentToolbar.setTitle(title);
    }
}
