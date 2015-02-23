package lt.eliga.u2020.ui.activity_main.image;

import android.support.annotation.NonNull;

import javax.inject.Inject;

import dagger.Module;
import dagger.Provides;
import lt.eliga.u2020.data.api.ImageService;
import lt.eliga.u2020.data.api.model.response.Image;
import lt.eliga.u2020.data.api.transforms.ImageResponseToImage;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

@Module
public class ImgurImageModule {
    private final @NonNull String imageId;

    public ImgurImageModule(String imageId) {
        this.imageId = imageId;
    }

    @Provides
    @Inject Observable<Image> provideImageObservable(ImageService imageService) {
        return imageService.image(imageId).
                map(new ImageResponseToImage()).
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread());
    }
}
