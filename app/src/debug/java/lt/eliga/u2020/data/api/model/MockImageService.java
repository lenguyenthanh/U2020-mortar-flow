package lt.eliga.u2020.data.api.model;

import lt.eliga.u2020.data.api.ImageService;
import lt.eliga.u2020.data.api.ServerDatabase;
import lt.eliga.u2020.data.api.model.response.ImageResponse;

import javax.inject.Inject;

import retrofit.http.Path;
import rx.Observable;

public class MockImageService implements ImageService {

    private final ServerDatabase serverDatabase;

    @Inject
    MockImageService(ServerDatabase serverDatabase) {
        this.serverDatabase = serverDatabase;
    }

    @Override
    public Observable<ImageResponse> image(@Path("id") String id) {
        return Observable.just(serverDatabase.getImageForId(id));
    }
}
