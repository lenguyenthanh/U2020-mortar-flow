package lt.eliga.u2020.data.api;

import dagger.Module;
import dagger.Provides;
import retrofit.Endpoint;
import retrofit.Endpoints;
import retrofit.RestAdapter;
import lt.eliga.u2020.ui.ApplicationScope;

@Module(includes = ApiModule.class)
public final class ReleaseApiModule {
    private static final String CLIENT_ID = "3436c108ccc17d3";

    @Provides
    @ApplicationScope
    Endpoint provideEndpoint() {
        return Endpoints.newFixedEndpoint(ApiModule.PRODUCTION_API_URL);
    }

    @Provides
    @ApplicationScope
    GalleryService provideGalleryService(RestAdapter restAdapter) {
        return restAdapter.create(GalleryService.class);
    }

    @Provides
    @ApplicationScope
    ImageService provideImageService(RestAdapter restAdapter) {
        return restAdapter.create(ImageService.class);
    }
}
