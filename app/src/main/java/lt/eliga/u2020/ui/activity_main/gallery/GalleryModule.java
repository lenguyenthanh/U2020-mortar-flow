package lt.eliga.u2020.ui.activity_main.gallery;

import dagger.Module;
import dagger.Provides;
import lt.eliga.u2020.data.api.model.request.Section;
import lt.eliga.u2020.ui.ApplicationScope;

/**
 * Created by eliga on 22/02/15.
 */
@ApplicationScope
@Module
public class GalleryModule {
    @Provides Section providesSection() {
        return Section.HOT;
    }
}
