package lt.eliga.u2020.ui.activity_main.gallery;

import android.content.Context;
import android.support.v4.util.Pair;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import mortar.dagger2support.DaggerService;
import lt.eliga.u2020.R;
import lt.eliga.u2020.core.navigation_activity.ActivityScreen;
import lt.eliga.u2020.core.navigation_activity.ScreenSwitcher;
import lt.eliga.u2020.data.api.model.response.Image;
import lt.eliga.u2020.ui.activity_about.AboutActivity;
import lt.eliga.u2020.ui.misc.BetterViewAnimator;
import rx.Observable;
import rx.android.widget.OnItemClickEvent;
import rx.android.widget.WidgetObservable;
import rx.functions.Func1;

import static mortar.MortarScope.getScope;

public class GalleryView extends BetterViewAnimator implements View.OnClickListener {
    @InjectView(R.id.gallery_grid) AbsListView galleryView;
    @InjectView(R.id.button)       Button about;
    @Inject                        GalleryScreen.Presenter presenter;
    @Inject                        ScreenSwitcher screenSwitcher;
    private                        GalleryAdapter adapter;

    public GalleryView(Context context, AttributeSet attrs) {
        super(context, attrs);
        getScope(getContext()).<GalleryScreen.Component>getService(
                DaggerService.SERVICE_NAME
        ).inject(this);
    }

    @Override protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.inject(this);
        about.setOnClickListener(this);
        adapter = new GalleryAdapter(getContext(), presenter.getPicasso());
        setAdapter(adapter);
        presenter.takeView(this);
    }

    public Observable<Pair<Image, GalleryItemView>> observeImageClicks() {
        return WidgetObservable.itemClicks(galleryView).map(new OnItemClickEventToImage(adapter));
    }

    private static final class OnItemClickEventToImage
            implements Func1<OnItemClickEvent, Pair<Image, GalleryItemView>> {

        private final GalleryAdapter adapter;

        private OnItemClickEventToImage(GalleryAdapter adapter) {
            this.adapter = adapter;
        }

        @Override
        public Pair<Image, GalleryItemView> call(OnItemClickEvent onItemClickEvent) {
            Image image = adapter.getItem(onItemClickEvent.position());
            GalleryItemView view = (GalleryItemView) onItemClickEvent.view();
            return new Pair<>(image, view);
        }
    }

    @Override protected void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    @Override protected void onDetachedFromWindow() {
        presenter.dropView(this);
        super.onDetachedFromWindow();
    }

    public void setAdapter(GalleryAdapter adapter) {
        galleryView.setAdapter(adapter);
    }

    public void updateChildId() {
        setDisplayedChildId(R.id.content);
    }

    public GalleryAdapter getAdapter() {
        return adapter;
    }

    @Override public void onClick(View v) {
        ActivityScreen screen = new AboutActivity.Screen("lopas");
        screen.attachTransitionView(this);
        screenSwitcher.open(screen);
    }

}