package lt.eliga.u2020.ui.activity_about.about;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import lt.eliga.u2020.R;
import lt.eliga.u2020.ui.misc.BetterViewAnimator;
import mortar.dagger2support.DaggerService;

import static mortar.MortarScope.getScope;

public class AboutView extends BetterViewAnimator {
    @InjectView(R.id.textAbout) TextView              aboutText;
    @Inject                     AboutScreen.Presenter presenter;

    public AboutView(Context context, AttributeSet attrs) {
        super(context, attrs);
        getScope(getContext()).<AboutScreen.Component>getService(
                DaggerService.SERVICE_NAME
        ).inject(this);
    }

    @Override protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.inject(this);
        presenter.takeView(this);
        aboutText.setText("Everything about nothing");
    }

    @Override protected void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    @Override protected void onDetachedFromWindow() {
        presenter.dropView(this);
        super.onDetachedFromWindow();
    }
}