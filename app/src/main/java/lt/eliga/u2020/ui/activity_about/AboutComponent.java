package lt.eliga.u2020.ui.activity_about;

import dagger.Component;
import lt.eliga.u2020.U2020Component;
import lt.eliga.u2020.ui.ScopeSingleton;
import lt.eliga.u2020.ui.activity_about.about.AboutView;

@ScopeSingleton(AboutComponent.class)
@Component(
        dependencies = U2020Component.class
)
public interface AboutComponent {
    void inject(AboutActivity activity);
    void inject(AboutView view);
}
