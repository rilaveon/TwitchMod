package tv.twitch.android.mod.swipper.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.RelativeLayout;
import android.widget.TextView;

import static tv.twitch.android.mod.swipper.util.DimensionConverter.dipToPix;

public class SwipperOverlay extends RelativeLayout {
    private final static int ANIMATION_DURATION = 500;
    private final static int MAX_BRIGHTNESS = 100;
    private final static int PROGRESS_TEXT_SIZE = 45;
    private final static float SHADOW_SIZE = 2.0f;

    private final Context mContext;
    private final VerticalProgressBar volumeProgressBar;
    private final VerticalProgressBar brightnessProgressBar;
    private final TextView progress;

    public SwipperOverlay(Context context) {
        super(context);
        mContext = context;
        volumeProgressBar = new VerticalProgressBar(context);
        brightnessProgressBar = new VerticalProgressBar(context);
        progress = new TextView(context);
        initialize();
    }

    private void initialize() {
        this.setId(View.generateViewId());
        this.setGravity(Gravity.CENTER_VERTICAL);
        this.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        RelativeLayout.LayoutParams volumeBarParams = new RelativeLayout.LayoutParams(dipToPix(mContext, 14), dipToPix(mContext, 110));
        volumeBarParams.addRule(RelativeLayout.CENTER_VERTICAL);
        volumeBarParams.setMargins(dipToPix(mContext, 12), 0, dipToPix(mContext, 10), 0);
        volumeProgressBar.setLayoutParams(volumeBarParams);
        volumeProgressBar.setVisibility(INVISIBLE);
        this.addView(volumeProgressBar, volumeBarParams);

        RelativeLayout.LayoutParams brightnessBarParams = new RelativeLayout.LayoutParams(dipToPix(mContext, 14), dipToPix(mContext, 110));
        brightnessBarParams.addRule(RelativeLayout.CENTER_VERTICAL);
        brightnessBarParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        brightnessBarParams.setMargins(dipToPix(mContext, 12), 0, dipToPix(mContext, 10), 0);
        brightnessProgressBar.setLayoutParams(brightnessBarParams);
        brightnessProgressBar.setMax(MAX_BRIGHTNESS);
        brightnessProgressBar.setVisibility(INVISIBLE);
        this.addView(brightnessProgressBar, brightnessBarParams);

        progress.setId(View.generateViewId());
        progress.setTextSize(PROGRESS_TEXT_SIZE);
        progress.setTextColor(Color.WHITE);
        progress.setPadding(5,5,5,5);
        progress.setShadowLayer(SHADOW_SIZE, 0.0f, 0.0f, Color.BLACK);

        RelativeLayout.LayoutParams textParam = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        textParam.addRule(RelativeLayout.CENTER_VERTICAL);
        textParam.addRule(RelativeLayout.CENTER_HORIZONTAL);
        progress.setLayoutParams(textParam);
        progress.setVisibility(INVISIBLE);
        this.addView(progress, textParam);
    }

    public void showVolume() {
        volumeProgressBar.animate().cancel();

        if (volumeProgressBar.getVisibility() != VISIBLE) {
            volumeProgressBar.setAlpha(1f);
            volumeProgressBar.setVisibility(VISIBLE);
        }

        showProgressText();
    }

    private void showProgressText() {
        progress.animate().cancel();

        if (progress.getVisibility() != VISIBLE) {
            progress.setAlpha(1f);
            progress.setVisibility(VISIBLE);
        }
    }

    public void showBrightness() {
        brightnessProgressBar.animate().cancel();

        if (brightnessProgressBar.getVisibility() != VISIBLE) {
            brightnessProgressBar.setAlpha(1f);
            brightnessProgressBar.setVisibility(VISIBLE);
        }

        showProgressText();
    }

    private void hideProgressText() {
        progress.animate().cancel();
        progress.animate()
                .alpha(0f)
                .setDuration(ANIMATION_DURATION)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        progress.setVisibility(View.GONE);
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {
                        progress.setVisibility(View.GONE);
                    }
                });
    }

    public void hideVolume() {
        volumeProgressBar.animate().cancel();
        volumeProgressBar.animate()
                .alpha(0f)
                .setDuration(ANIMATION_DURATION)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        volumeProgressBar.setVisibility(GONE);
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {
                        volumeProgressBar.setVisibility(GONE);
                    }
                });

        hideProgressText();
    }

    public void setVolume(int index) {
        volumeProgressBar.setProgress(index);
        progress.setText(Integer.toString(index));
    }

    public void setMaxVolume(int max) {
        volumeProgressBar.setMax(max);
    }

    public int getMaxVolume() {
        return volumeProgressBar.getMax();
    }

    public void hideBrightness() {
        brightnessProgressBar.animate().cancel();
        brightnessProgressBar.animate()
                .alpha(0f)
                .setDuration(ANIMATION_DURATION)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        brightnessProgressBar.setVisibility(GONE);
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {
                        brightnessProgressBar.setVisibility(GONE);
                    }
                });

        hideProgressText();
    }

    public void setBrightness(int index) {
        brightnessProgressBar.setProgress(index);
        progress.setText(Integer.toString(index));
    }

    public int getMaxBrightness() {
        return brightnessProgressBar.getMax();
    }
}
