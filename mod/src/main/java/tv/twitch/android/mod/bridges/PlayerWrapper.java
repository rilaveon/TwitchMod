package tv.twitch.android.mod.bridges;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import tv.twitch.android.mod.settings.PreferenceManager;
import tv.twitch.android.mod.utils.Logger;


public class PlayerWrapper extends RelativeLayout {
    private ViewGroup mPlayerOverlayContainer;
    private ViewGroup mDebugPanelContainer;
    private ViewGroup mFloatingChatContainer;

    private final PlayerWrapperDelegate mPlayerWrapperDelegate;


    public PlayerWrapper(Context context) {
        this(context, null);
    }

    public PlayerWrapper(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PlayerWrapper(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPlayerWrapperDelegate = new PlayerWrapperDelegate(this, (Activity) context);
    }

    public ViewGroup getPlayerOverlayContainer() {
        return mPlayerOverlayContainer;
    }

    public ViewGroup getDebugPanelContainer() {
        return mDebugPanelContainer;
    }

    public ViewGroup getFloatingChatContainer() {
        return mFloatingChatContainer;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        mPlayerOverlayContainer = findViewById(ResourcesManager.getId("player_overlay_container"));
        if (mPlayerOverlayContainer == null) {
            Logger.error("mPlayerOverlayContainer is null. Update ID?");
            return;
        }

        mFloatingChatContainer = findViewById(ResourcesManager.getId("floating_chat_container"));
        if (mFloatingChatContainer == null) {
            Logger.error("mFloatingChatContainer is null. Update ID?");
            return;
        }

        mDebugPanelContainer = findViewById(ResourcesManager.getId("debug_panel_container"));
        if (mDebugPanelContainer == null) {
            Logger.error("mDebugPanelContainer is null. Update ID?");
            return;
        }

        initializeSwipper();
    }

    private boolean needInterceptTouch() {
        return mPlayerWrapperDelegate.isEnabled() && !PreferenceManager.INSTANCE.shouldLockSwiper();
    }

    private void initializeSwipper() {
        mPlayerWrapperDelegate.setOverlay(mPlayerOverlayContainer);
        PreferenceManager manager = PreferenceManager.INSTANCE;

        mPlayerWrapperDelegate.setVolumeEnabled(manager.isVolumeSwiperEnabled());
        mPlayerWrapperDelegate.setBrightnessEnabled(manager.isBrightnessSwiperEnabled());
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        if (!needInterceptTouch())
            return false;

        return mPlayerWrapperDelegate.onInterceptTouchEvent(event);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return mPlayerWrapperDelegate.onTouchEvent(event);
    }
}