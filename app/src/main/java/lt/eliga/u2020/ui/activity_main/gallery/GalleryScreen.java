package lt.eliga.u2020.ui.activity_main.gallery;

import android.os.Bundle;
import android.support.v4.util.Pair;

import com.squareup.picasso.Picasso;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import flow.Flow;
import flow.Layout;
import flow.Path;
import lt.eliga.u2020.ui.activity_main.MainActivity;
import mortar.MortarScope;
import mortar.ViewPresenter;
import lt.eliga.u2020.R;
import lt.eliga.u2020.core.navigation_screen.mortar.DynamicModules;
import lt.eliga.u2020.data.GalleryDatabase;
import lt.eliga.u2020.data.api.model.request.Section;
import lt.eliga.u2020.data.api.model.response.Image;
import lt.eliga.u2020.data.rx.EndlessObserver;
import lt.eliga.u2020.core.ScopeSingleton;
import lt.eliga.u2020.core.WithComponent;
import lt.eliga.u2020.ui.activity_main.image.ImageScreen;
import rx.Subscription;
import rx.functions.Action1;
import timber.log.Timber;

/**
 * Created by eliga on 14/02/15.
 */

@Layout(R.layout.gallery_view) @WithComponent(GalleryScreen.Component.class)
public class GalleryScreen extends Path implements DynamicModules {

    @Override public List<Object> dependencies() {
        return Arrays.<Object>asList(new GalleryModule());
    }

    @dagger.Component(
            dependencies = MainActivity.ActivityComponent.class,
            modules = {GalleryModule.class}
    )
    @ScopeSingleton(Component.class)
    public interface Component {
        void inject(GalleryView view);
    }

    @ScopeSingleton(Component.class)
    public static class Presenter extends ViewPresenter<GalleryView> {

        private final GalleryDatabase galleryDatabase;
        private final Picasso         picasso;
        private final Section         section;

        private Subscription request;
        private Subscription clicks;

        @Inject Presenter(
                GalleryDatabase galleryDatabase,
                Picasso picasso,
                Section section
        ) {
            this.galleryDatabase = galleryDatabase;
            this.picasso = picasso;
            this.section = section;
        }

        @Override public void onLoad(Bundle savedInstanceState) {
            super.onLoad(savedInstanceState);

            if (!hasView()) return;

            request = galleryDatabase.loadGallery(
                    section, new EndlessObserver<List<Image>>() {
                        @Override public void onNext(List<Image> images) {
                            if (getView() != null) {
                                getView().getAdapter().replaceWith(images);
                                getView().updateChildId();
                            }
                        }
                    }
            );

            clicks = getView().observeImageClicks().subscribe(
                    new Action1<Pair<Image, GalleryItemView>>() {
                        @Override
                        public void call(Pair<Image, GalleryItemView> image) {
                            Timber.d("Image clicked with id = %s", image.first.id);
                            Flow.get(getView().getContext()).goTo(new ImageScreen(image.first.id));
                        }
                    }
            );

        }


        @Override public void onEnterScope(MortarScope scope) {
            super.onEnterScope(scope);
        }

        @Override public void dropView(GalleryView view) {
            request.unsubscribe();
            clicks.unsubscribe();
            super.dropView(view);
        }

        public Picasso getPicasso() {
            return picasso;
        }
    }
}
