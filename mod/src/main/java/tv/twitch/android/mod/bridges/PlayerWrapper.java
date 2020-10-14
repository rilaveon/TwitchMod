package tv.twitch.android.mod.bridges;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import tv.twitch.android.mod.settings.PreferenceManager;
import tv.twitch.android.mod.swipper.Swipper;
import tv.twitch.android.mod.utils.Logger;
import tv.twitch.android.mod.utils.ViewUtil;


public class PlayerWrapper extends RelativeLayout {
    private static final int PADDING_DEFAULT_IGNORE = 30;

    private ViewGroup mPlayerOverlayContainer;
    private ViewGroup mDebugPanelContainer;
    private ViewGroup mFloatingChatContainer;

    private final Swipper mSwipper;

    private final int mTouchSlop;
    private final int mPaddingDeviceIgnore;

    private int mStartPosY = -1;
    private int mStartPosX = -1;
    private boolean bInScrollArea = false;


    public PlayerWrapper(Context context) {
        this(context, null);
    }

    public PlayerWrapper(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PlayerWrapper(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mSwipper = new Swipper((Activity) context);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop() * 2;
        mPaddingDeviceIgnore = Math.round(PADDING_DEFAULT_IGNORE * context.getResources().getDisplayMetrics().density);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        ResourcesManager resourcesManager = ResourcesManager.INSTANCE;

        mPlayerOverlayContainer = findViewById(resourcesManager.getId("player_overlay_container"));
        if (mPlayerOverlayContainer == null) {
            Logger.error("mPlayerOverlayContainer is null. Update ID?");
            return;
        }

        mFloatingChatContainer = findViewById(resourcesManager.getId("floating_chat_container"));
        if (mFloatingChatContainer == null) {
            Logger.error("mFloatingChatContainer is null. Update ID?");
            return;
        }

        mDebugPanelContainer = findViewById(resourcesManager.getId("debug_panel_container"));
        if (mDebugPanelContainer == null) {
            Logger.error("mDebugPanelContainer is null. Update ID?");
            return;
        }

        initializeSwipper();
    }

    private boolean isSwipperAllowed() {
        return mSwipper.isEnabled() && !PreferenceManager.INSTANCE.shouldLockSwiper();
    }

    private void initializeSwipper() {
        mSwipper.setOverlay(mPlayerOverlayContainer);

        if (PreferenceManager.INSTANCE.isVolumeSwiperEnabled())
            mSwipper.enableVolumeSwipe();

        if (PreferenceManager.INSTANCE.isBrightnessSwiperEnabled())
            mSwipper.enableBrightnessSwipe();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        if (!isSwipperAllowed())
            return false;

        final int action = event.getAction();

        switch (action) {
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                mStartPosY = -1;
                mStartPosX = -1;

                return mSwipper.onTouchEvent(event);
            case MotionEvent.ACTION_DOWN:
                mStartPosY = Math.round(event.getY());
                mStartPosX = Math.round(event.getX());

                bInScrollArea = checkArea(event);

                if (bInScrollArea)
                    mSwipper.onTouchEvent(event);

                return false;
            case MotionEvent.ACTION_MOVE: {
                if (!bInScrollArea)
                    return false;

                if (event.getPointerCount() > 1) {
                    Logger.debug("Ignore scrolling: multi touch, val="+event.getPointerCount());
                    bInScrollArea = false;
                    return false;
                }

                int diff = getDistance(event);
                if (diff > mTouchSlop) {
                    Logger.debug("SCROLLING");
                    return mSwipper.onTouchEvent(event);
                }
                break;
            }
            case MotionEvent.ACTION_POINTER_DOWN:
                bInScrollArea = false;
                return false;
        }

        return false;
    }

    private boolean checkCollisions() {
        if (!ViewUtil.isHit(mPlayerOverlayContainer, mStartPosX, mStartPosY)) {
            Logger.debug("Ignore scrolling: Wrong area: x=" + mStartPosX + ", y="+ mStartPosY);
            return false;
        }

        if (ViewUtil.isVisible(mDebugPanelContainer)) {
            ViewGroup list = mDebugPanelContainer.findViewById(ResourcesManager.INSTANCE.getId("video_debug_list"));
            if (ViewUtil.isVisible(ViewUtil.getFirstChild(mDebugPanelContainer)) && ViewUtil.isVisible(list) && ViewUtil.isHit(list, mStartPosX, mStartPosY)) {
                Logger.debug("Ignore scrolling: Debug panel area: x=" + mStartPosX + ", y=" + mStartPosY);
                return false;
            }
        }

        if (ViewUtil.isVisible(mFloatingChatContainer)) {
            ViewGroup container = mFloatingChatContainer.findViewById(ResourcesManager.INSTANCE.getId("messages_container"));
            if (ViewUtil.isVisible(container) && ViewUtil.isHit(container, mStartPosX, mStartPosY)) {
                Logger.debug("Ignore scrolling: Floating chat area: x=" + mStartPosX + ", y=" + mStartPosY);
                return false;
            }
        }

        return true;
    }

    private boolean checkArea(MotionEvent event) {
        if (mStartPosY <= mPaddingDeviceIgnore) {
            Logger.debug("Ignore scrolling: top PADDING_IGNORE=" + mPaddingDeviceIgnore +", val="+ mStartPosY);
            return false;
        }

        float overlayBottomY = mPlayerOverlayContainer.getY() + mPlayerOverlayContainer.getHeight();
        if (mStartPosY >= (overlayBottomY - mPaddingDeviceIgnore)) {
            Logger.debug("Ignore scrolling: bottom PADDING_IGNORE=" + mPaddingDeviceIgnore +", val="+ overlayBottomY);
            return false;
        }

        if (event.getPointerCount() > 1) {
            Logger.debug("Ignore scrolling: multi touch, val="+event.getPointerCount());
            return false;
        }

        if (getResources().getConfiguration().orientation != Configuration.ORIENTATION_LANDSCAPE) {
            Logger.debug("Ignore scrolling: wrong orientation");
            return false;
        }

        return checkCollisions();
    }

    private int getDistance(MotionEvent moveEvent) {
        if (moveEvent == null) {
            Logger.debug("moveEvent is null");
            return 0;
        }

        if (mStartPosY == -1) {
            Logger.debug("startPosY == -1");
            return 0;
        }

        return Math.abs(mStartPosY - Math.round(moveEvent.getY()));
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return mSwipper.onTouchEvent(event);
    }
}