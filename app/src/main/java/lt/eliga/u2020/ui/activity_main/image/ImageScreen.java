package lt.eliga.u2020.ui.activity_main.image;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import flow.HasParent;
import flow.Layout;
import flow.Path;
import lt.eliga.u2020.R;
import lt.eliga.u2020.core.navigation_activity.ToolbarPresenter;
import lt.eliga.u2020.core.navigation_screen.mortar.DynamicModules;
import lt.eliga.u2020.data.api.model.response.Image;
import lt.eliga.u2020.ui.ScopeSingleton;
import lt.eliga.u2020.ui.WithComponent;
import lt.eliga.u2020.ui.activity_main.gallery.GalleryScreen;
import rx.Observable;
import rx.Subscription;
import rx.functions.Action1;
import timber.log.Timber;

/**
 * Created by eliga on 14/02/15.
 */

@Layout(R.layout.gallery_view_image) @WithComponent(ImageComponent.class)
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

    @ScopeSingleton(ImageComponent.class)
    public static class Presenter
            extends lt.eliga.u2020.ui._activity_base.ViewPresenter<ImgurImageView> {

        private final Observable<Image> imageObservable;
        private final ToolbarPresenter  toolbarPresenter;
        private       Subscription      subscription;

        @Inject
        public Presenter(Observable<Image> imageObservable, ToolbarPresenter toolbarPresenter) {
            this.imageObservable = imageObservable;
            this.toolbarPresenter = toolbarPresenter;
        }

        @Override
        protected void onLoad() {
            super.onLoad();
            Timber.d("Loading image");
            subscription = imageObservable.
                    subscribe(
                            new Action1<Image>() {
                                @Override
                                public void call(Image image) {
                                    Timber.d("Image loaded with id %s", image.toString());
                                    getView().bindTo(image);
                                    toolbarPresenter.setTitle(image.title);
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

        @Override
        protected void onDestroy() {
            super.onDestroy();
            subscription.unsubscribe();
        }
    }
}
