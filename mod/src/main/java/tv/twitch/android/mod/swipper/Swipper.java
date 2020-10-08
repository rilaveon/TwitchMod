package tv.twitch.android.mod.swipper;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.os.Handler;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import tv.twitch.android.mod.swipper.util.BrightnessHelper;
import tv.twitch.android.mod.swipper.view.SwipperOverlay;


public class Swipper extends GestureDetector.SimpleOnGestureListener {
    private final static int DELAY_TIMEOUT_MILLISECONDS = 500;
    private final static float HEIGHT_FACTOR = 0.7f;

    private final SwipperOverlay mOverlay;
    private final GestureDetector mGestureDetector;

    private final Activity mContext;
    private final AudioManager mAudioManager;

    private final Handler mHandler;
    private Runnable mProgressHide;

    private boolean bIsLeftArea = false;

    private int mOldVolume;
    private int mOldBrightness;
    private int mCurrentVolume;
    private int mCurrentBrightness;

    private boolean bIsVolumeSwipeEnabled;
    private boolean bIsBrightnessSwipeEnabled;


    public Swipper(Activity activity) {
        mContext = activity;
        mHandler = new Handler();

        mGestureDetector = new GestureDetector(mContext, this);
        mAudioManager = (AudioManager) activity.getSystemService(Context.AUDIO_SERVICE);

        bIsVolumeSwipeEnabled = false;
        bIsBrightnessSwipeEnabled = false;

        mOverlay = new SwipperOverlay(mContext);
    }

    public void setOverlay(ViewGroup viewGroup) {
        RelativeLayout.LayoutParams overlayParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);

        RelativeLayout relativeLayout = new RelativeLayout(mContext);
        relativeLayout.setGravity(Gravity.CENTER);
        relativeLayout.addView(mOverlay);

        mOverlay.setMaxVolume(getSystemMaxVolume());
        mOverlay.setVolume(getSystemVolume());
        mOverlay.setBrightness(BrightnessHelper.getWindowBrightness(mContext));

        viewGroup.addView(relativeLayout, overlayParams);
        mOverlay.setVisibility(View.VISIBLE);
        mOverlay.invalidate();
        mOverlay.requestLayout();
    }

    public boolean isEnabled() {
        return bIsVolumeSwipeEnabled || bIsBrightnessSwipeEnabled;
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (!isEnabled())
            return false;

        switch (motionEvent.getActionMasked()) {
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                delayHideProgress();
        }

        return mGestureDetector.onTouchEvent(motionEvent);
    }

    private void delayHideProgress() {
        if (mProgressHide != null) {
            synchronized (mHandler) {
                if (mProgressHide != null) {
                    mHandler.removeCallbacks(mProgressHide);
                    mProgressHide = null;
                }
            }
        }

        mProgressHide = new Runnable() {
            @Override
            public void run() {
                hideProgress();
            }
        };

        mHandler.postDelayed(mProgressHide, DELAY_TIMEOUT_MILLISECONDS);
    }

    @Override
    public boolean onDown(MotionEvent e) {
        mOldBrightness = BrightnessHelper.getWindowBrightness(mContext);
        mOldVolume = getSystemVolume();

        mCurrentBrightness = mOldBrightness;
        mCurrentVolume = mOldVolume;

        bIsLeftArea = e.getX() < mOverlay.getWidth() / 2.0f;

        return false;
    }

    @Override
    public boolean onScroll(MotionEvent downEvent, MotionEvent moveEvent, float distanceX, float distanceY) {
        final float diff = downEvent.getY() - moveEvent.getY();

        if (bIsLeftArea) {
            if (!bIsBrightnessSwipeEnabled)
                return false;

            updateBrightnessProgress(diff);
        } else {
            if (!bIsVolumeSwipeEnabled)
                return false;

            updateVolumeProgress(diff);
        }

        return true;
    }

    public void enableVolumeSwipe() {
        bIsVolumeSwipeEnabled = true;
    }

    public void enableBrightnessSwipe() {
        bIsBrightnessSwipeEnabled = true;
    }

    public int getSystemMaxVolume() {
        return mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
    }

    public int getSystemVolume() {
        return mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
    }

    public void setSystemVolume(int index) {
        mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, index, 0);
    }

    private int getOverlayHeight() {
        return mOverlay.getHeight();
    }

    private int getMaxVolume() {
        return mOverlay.getMaxVolume();
    }

    private int getMaxBrightness() {
        return mOverlay.getMaxBrightness();
    }

    private int calculate(float delta, int oldStep, int max) {
        float height = getOverlayHeight() * HEIGHT_FACTOR;

        float step = height / max;
        int diff = (int) (delta / step);

        return Math.max(0, Math.min(max, oldStep + diff));
    }

    private void updateVolumeProgress(float delta) {
        int val = calculate(delta, mOldVolume, getMaxVolume());

        if (mCurrentVolume != val) {
            setSystemVolume(val);
            mCurrentVolume = val;
            mOverlay.setVolume(val);
        }

        mOverlay.showVolume();
    }

    private void updateBrightnessProgress(float delta) {
        int val = calculate(delta, mOldBrightness, getMaxBrightness());

        if (mCurrentBrightness != val) {
            BrightnessHelper.setWindowBrightness(mContext, val);
            mCurrentBrightness = val;
            mOverlay.setBrightness(val);
        }

        mOverlay.showBrightness();
    }

    public void hideProgress() {
        if (mProgressHide != null) {
            synchronized (mHandler) {
                if (mProgressHide != null) {
                    mHandler.removeCallbacks(mProgressHide);
                    mProgressHide = null;
                }
            }
        }

        mOverlay.hideVolume();
        mOverlay.hideBrightness();
    }

    @Override
    public void onShowPress(MotionEvent e) {}

    @Override
    public void onLongPress(MotionEvent e) {}

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    public boolean onSingleTapConfirmed(MotionEvent motionEvent) {
         return false;
    }
}
