package lt.eliga.u2020.ui.activity_main.image;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import lt.eliga.u2020.R;
import lt.eliga.u2020.data.api.model.response.Image;
import lt.eliga.u2020.ui.activity_main.gallery.GalleryScreen;
import lt.eliga.u2020.ui.misc.BetterViewAnimator;
import mortar.dagger2support.DaggerService;

import static mortar.MortarScope.getScope;

public class ImgurImageView extends BetterViewAnimator {

    @Inject                 Picasso                 picasso;
    @InjectView(R.id.image) ImageView               imageView;
    @Inject                 ImageScreen.Presenter presenter;


    public ImgurImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        getScope(getContext()).<ImageScreen.Component>getService(
                DaggerService.SERVICE_NAME
        ).inject(this);
    }

    @Override protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.inject(this);
        presenter.takeView(this);
    }

    public void bindTo(Image image) {
        picasso.load(image.link).into(imageView);
    }

    @Override protected void onDetachedFromWindow() {
        presenter.dropView(this);
        super.onDetachedFromWindow();
    }

}
