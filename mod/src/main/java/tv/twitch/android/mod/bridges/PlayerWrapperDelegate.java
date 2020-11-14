package tv.twitch.android.mod.bridges;


import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.media.AudioManager;
import android.os.Handler;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import tv.twitch.android.mod.swipper.util.BrightnessHelper;
import tv.twitch.android.mod.swipper.view.SwipperOverlay;
import tv.twitch.android.mod.utils.Logger;
import tv.twitch.android.mod.utils.ViewUtil;


public class PlayerWrapperDelegate {
    private static final int PADDING_DEFAULT_IGNORE = 30;
    private final static int DELAY_TIMEOUT_MILLISECONDS = 500;
    private final static float GESTURE_SCALE_FACTORY = 0.7f;

    private final SwipperOverlay mOverlay;

    private final Activity mContext;
    private final AudioManager mAudioManager;

    private final Handler mHandler;
    private Runnable mProgressHide;

    private final PlayerWrapper playerWrapper;

    private boolean inLeftArea = false;

    private int mOldVolume;
    private int mOldBrightness;

    private boolean isVolumeSwipeEnabled;
    private boolean isBrightnessSwipeEnabled;

    private final int mTouchSlop;
    private final int mPaddingDeviceIgnore;

    private float initTouchPosX = -1;
    private float initTouchPosY = -1;

    private boolean inScrollArea = false;
    private boolean inScrollMode = false;


    public PlayerWrapperDelegate(PlayerWrapper wrapper, Activity activity) {
        mContext = activity;
        playerWrapper = wrapper;

        mAudioManager = (AudioManager) activity.getSystemService(Context.AUDIO_SERVICE);

        mHandler = new Handler();

        mTouchSlop = ViewConfiguration.get(mContext).getScaledTouchSlop() * 2;
        mPaddingDeviceIgnore = Math.round(PADDING_DEFAULT_IGNORE * mContext.getResources().getDisplayMetrics().density);

        isVolumeSwipeEnabled = false;
        isBrightnessSwipeEnabled = false;

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
        return isVolumeSwipeEnabled || isBrightnessSwipeEnabled;
    }

    public void setVolumeEnabled(boolean state) {
        isVolumeSwipeEnabled = state;
    }

    public void setBrightnessEnabled(boolean state) {
        isBrightnessSwipeEnabled = state;
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
        float height = getOverlayHeight() * GESTURE_SCALE_FACTORY;

        float step = height / max;
        int diff = (int) (delta / step);

        return Math.max(0, Math.min(max, oldStep + diff));
    }

    private void updateVolumeProgress(float delta) {
        int val = calculate(delta, mOldVolume, getMaxVolume());

        setSystemVolume(val);
        mOverlay.setVolume(val);

        mOverlay.showVolume();
    }

    private void updateBrightnessProgress(float delta) {
        int val = calculate(delta, mOldBrightness, getMaxBrightness());

        BrightnessHelper.setWindowBrightness(mContext, val);
        mOverlay.setBrightness(val);

        mOverlay.showBrightness();
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

    public boolean onTouchEvent(MotionEvent motionEvent) {
        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                if (inScrollMode) {
                    delayHideProgress();
                }

                inScrollMode = false;

                return false;
            case MotionEvent.ACTION_DOWN:
                mOldBrightness = BrightnessHelper.getWindowBrightness(mContext);
                mOldVolume = getSystemVolume();

                inLeftArea = motionEvent.getX() < mOverlay.getWidth() / 2.0f;
                initTouchPosX = motionEvent.getX();
                initTouchPosY = motionEvent.getY();

                return false;
            case MotionEvent.ACTION_MOVE:
                float delta = initTouchPosY - motionEvent.getY();
                if (!inScrollMode) {
                    hideProgress();
                    inScrollMode = true;
                }

                if (inLeftArea)
                    updateBrightnessProgress(delta);
                else
                    updateVolumeProgress(delta);

                return true;
        }

        return false;
    }

    public boolean onInterceptTouchEvent(MotionEvent event) {
        final int action = event.getAction();

        switch (action) {
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                initTouchPosX = -1;
                initTouchPosY = -1;

                onTouchEvent(event);

                return false;
            case MotionEvent.ACTION_DOWN:
                initTouchPosY = Math.round(event.getY());
                initTouchPosX = Math.round(event.getX());

                inScrollArea = checkArea(event);

                if (inScrollArea)
                    onTouchEvent(event);

                return false;
            case MotionEvent.ACTION_MOVE: {
                if (!inScrollArea)
                    return false;

                if (event.getPointerCount() > 1) {
                    Logger.debug("Ignore scrolling: multi touch, val="+event.getPointerCount());
                    inScrollArea = false;
                    return false;
                }

                if (Math.abs(initTouchPosY - event.getY()) > mTouchSlop) {
                    if (inLeftArea) {
                        if (!isBrightnessSwipeEnabled)
                            return false;
                    } else {
                        if (!isVolumeSwipeEnabled)
                            return false;
                    }

                    return onTouchEvent(event);
                }
                break;
            }
            case MotionEvent.ACTION_POINTER_DOWN:
                inScrollArea = false;
                return false;
        }

        return false;
    }

    private boolean checkCollisions() {
        final ViewGroup playerContainer = playerWrapper.getPlayerOverlayContainer();
        if (!ViewUtil.isHit(playerContainer, (int) initTouchPosX, (int) initTouchPosY)) {
            return false;
        }

        final ViewGroup debugContainer = playerWrapper.getDebugPanelContainer();
        if (ViewUtil.isVisible(debugContainer)) {
            ViewGroup list = (ViewGroup) ViewUtil.findViewByName(debugContainer, "video_debug_list");
            if (ViewUtil.isVisible(ViewUtil.getFirstChild(debugContainer)) && ViewUtil.isVisible(list) && ViewUtil.isHit(list, (int) initTouchPosX, (int) initTouchPosY))
                return false;
        }

        final ViewGroup floatingChatContainer = playerWrapper.getFloatingChatContainer();
        if (ViewUtil.isVisible(floatingChatContainer)) {
            ViewGroup container = (ViewGroup) ViewUtil.findViewByName(floatingChatContainer, "messages_container");
            if (ViewUtil.isVisible(container) && ViewUtil.isHit(container, (int) initTouchPosX, (int) initTouchPosY)) {
                return false;
            }
        }

        return true;
    }

    private boolean checkArea(MotionEvent event) {
        if (initTouchPosY <= mPaddingDeviceIgnore) {
            Logger.debug("Ignore scrolling: top PADDING_IGNORE=" + mPaddingDeviceIgnore +", val="+ initTouchPosY);
            return false;
        }


        final ViewGroup playerContainer = playerWrapper.getPlayerOverlayContainer();
        float overlayBottomY = playerContainer.getY() + playerContainer.getHeight();
        if (initTouchPosY >= (overlayBottomY - mPaddingDeviceIgnore)) {
            Logger.debug("Ignore scrolling: bottom PADDING_IGNORE=" + mPaddingDeviceIgnore +", val="+ overlayBottomY);
            return false;
        }

        if (event.getPointerCount() > 1) {
            Logger.debug("Ignore scrolling: multi touch, val="+event.getPointerCount());
            return false;
        }

        if (mContext.getResources().getConfiguration().orientation != Configuration.ORIENTATION_LANDSCAPE) {
            Logger.debug("Ignore scrolling: wrong orientation");
            return false;
        }

        return checkCollisions();
    }
}
