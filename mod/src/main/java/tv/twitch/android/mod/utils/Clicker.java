package tv.twitch.android.mod.utils;


import android.view.View.OnClickListener;


public class Clicker implements Runnable {
    private final OnClickListener mListener;

    public Clicker(OnClickListener listener) {
        mListener = listener;
    }

    @Override
    public void run() {
        if (mListener != null) {
            mListener.onClick(null);
        }
    }
}
