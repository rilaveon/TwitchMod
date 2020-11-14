package tv.twitch.android.app.run;


import android.os.Bundle;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;

import tv.twitch.android.mod.swipper.SwipperDelegate;
import tv.twitch.android.mod.swipper.view.SwipperOverlay;


public class MainActivity extends AppCompatActivity {
    private SwipperDelegate delegate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        delegate = new SwipperDelegate(null, this);
        delegate.setOverlay((ViewGroup) getWindow().getDecorView().getRootView());
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return delegate.onTouchEvent(event);
    }
}
