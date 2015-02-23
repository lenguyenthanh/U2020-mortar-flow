package lt.eliga.u2020.ui.activity_main.image;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

import mortar.dagger2support.DaggerService;
import lt.eliga.u2020.data.api.model.response.Image;

public class ImgurImageView extends ImageView {

//    @Inject Picasso picasso;

    public ImgurImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        DaggerService.<ImageComponent>getDaggerComponent(context).inject(this);
    }

    public void bindTo(Image image) {
//        picasso.load(image.link).into(this);
    }

}
