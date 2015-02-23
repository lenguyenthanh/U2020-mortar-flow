package lt.eliga.u2020;

import com.squareup.picasso.Picasso;

import lt.eliga.u2020.core.navigation_activity.ActivityScreenSwitcher;
import lt.eliga.u2020.core.navigation_activity.ScreenSwitcher;
import lt.eliga.u2020.core.navigation_activity.ToolbarPresenter;
import lt.eliga.u2020.data.GalleryDatabase;
import lt.eliga.u2020.data.api.GalleryService;
import lt.eliga.u2020.data.api.ImageService;
import lt.eliga.u2020.ui.AppContainer;

/**
 * A common interface implemented by both the Release and Debug flavored components.
 */
public interface U2020DataGraph {
    Picasso picasso();
    GalleryDatabase galleryDatabase();
    GalleryService galleryService();
    ScreenSwitcher screenSwitcher();
    ImageService imageService();

}
