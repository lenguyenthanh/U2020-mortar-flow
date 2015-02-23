package lt.eliga.u2020.ui.activity_main.image;

import android.os.Bundle;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import flow.HasParent;
import flow.Layout;
import flow.Path;
import lt.eliga.u2020.R;
import lt.eliga.u2020.core.navigation_screen.mortar.DynamicModules;
import lt.eliga.u2020.data.api.model.response.Image;
import lt.eliga.u2020.core.ScopeSingleton;
import lt.eliga.u2020.core.WithComponent;
import lt.eliga.u2020.ui.activity_main.MainActivity;
import lt.eliga.u2020.ui.activity_main.gallery.GalleryScreen;
import mortar.ViewPresenter;
import rx.Observable;
import rx.Subscription;
import rx.functions.Action1;
import timber.log.Timber;

/**
 * Created by eliga on 14/02/15.
 */

@Layout(R.layout.imgur_image_view) @WithComponent(ImageScreen.Component.class)
public class ImageScreen extends Path implements HasParent, DynamicModules {

    private final ImgurImageModule module;

    public ImageScreen(String id) {
        this.module = new ImgurImageModule(id);
    }

    @Override public Path getParent() {
        return new GalleryScreen();
    }

    @Override public List<Object> dependencies() {
        return Arrays.<Object>asList(module);
    }

    @dagger.Component(
            dependencies = MainActivity.ActivityComponent.class,
            modules = {ImgurImageModule.class}
    )
    @ScopeSingleton(Component.class)
    public interface Component {
        void inject(ImgurImageView view);
    }

    @ScopeSingleton(Component.class)
    public static class Presenter
            extends ViewPresenter<ImgurImageView> {

        private final Observable<Image> imageObservable;
        private       Subscription      subscription;

        @Inject
        public Presenter(Observable<Image> imageObservable) {
            this.imageObservable = imageObservable;
        }


        @Override
        protected void onLoad(Bundle savedInstanceState) {
            super.onLoad(savedInstanceState);
            if (!hasView()) return;
            Timber.d("Loading image");
            subscription = imageObservable.
                    subscribe(
                            new Action1<Image>() {
                                @Override
                                public void call(Image image) {
                                    Timber.d("Image loaded with id %s", image.toString());
                                    getView().bindTo(image);
                                }
                            },
                            new Action1<Throwable>() {
                                @Override
                                public void call(Throwable throwable) {
                                    Timber.e(throwable, "Image loading error");
                                }
                            }
                    );
        }

        @Override public void dropView(ImgurImageView view) {
            subscription.unsubscribe();
            super.dropView(view);

        }
    }
}
