package tv.twitch.android.mod.utils;


import android.graphics.Rect;
import android.view.View;
import android.view.ViewGroup;

import tv.twitch.android.mod.bridges.ResourcesManager;


public class ViewUtil {
    public static View findViewByName(View view, String name) {
        if (view == null)
            return null;

        if (name == null || name.length() == 0)
            return null;

        int id = ResourcesManager.getId(name);
        if (id == 0)
            return null;

        return view.findViewById(id);
    }

    public static void setBackground(View view, Integer color) {
        if (view == null) {
            Logger.error("view is null");
            return;
        }

        if (color == null) {
            view.setBackground(null);
            return;
        }

        view.setBackgroundColor(color);
    }

    public static View getFirstChild(ViewGroup viewGroup) {
        if (viewGroup == null)
            return null;

        if (viewGroup.getChildCount() < 1)
            return null;

        return viewGroup.getChildAt(0);
    }

    public static boolean isHit(ViewGroup view, int x, int y) {
        if (view == null) {
            return false;
        }

        Rect hitRect = new Rect();
        view.getHitRect(hitRect);

        return hitRect.contains(x, y);
    }

    public static boolean isVisible(View view) {
        if (view == null)
            return false;

        return view.getVisibility() == View.VISIBLE;
    }
}
