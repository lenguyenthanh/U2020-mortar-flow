package lt.eliga.u2020;

import com.squareup.picasso.Picasso;

import lt.eliga.u2020.data.GalleryDatabase;
import lt.eliga.u2020.data.api.GalleryService;
import lt.eliga.u2020.data.api.ImageService;
import lt.eliga.u2020.ui.AppContainer;
import lt.eliga.u2020.core.navigation_activity.ActivityScreenSwitcher;
import lt.eliga.u2020.core.navigation_activity.ScreenSwitcher;
import lt.eliga.u2020.core.navigation_activity.ToolbarPresenter;

/**
 * A common interface implemented by both the Release and Debug flavored components.
 */
public interface U2020Graph {
    void inject(U2020App app);

    AppContainer appContainer();
    Picasso picasso();
    ScreenSwitcher screenSwitcher();
    ActivityScreenSwitcher activityScreenSwitcher();
    ImageService imageService();
    GalleryDatabase galleryDatabase();
    GalleryService galleryService();
    ToolbarPresenter toolbarPresenter();
}
