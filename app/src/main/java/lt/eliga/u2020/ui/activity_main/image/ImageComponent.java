package lt.eliga.u2020.ui.activity_main.image;

import dagger.Component;
import lt.eliga.u2020.ui.ScopeSingleton;

@ScopeSingleton(ImageComponent.class)
@Component(
        modules = {ImgurImageModule.class}
)
public interface ImageComponent {
    void inject(ImgurImageView view);
}
