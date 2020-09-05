package tv.twitch.android.shared.experiments.helpers;


import tv.twitch.android.mod.bridges.Hooks;


public class ClipfinityExperiment {
    /* ... */

    public final boolean isClipfinityPlayerEnabled() {
        if (Hooks.isForceOldClipsViewJump()) { // TODO: __JUMP_HOOK
            return false;
        }

        /* ... */

        return false;
    }

    /* ... */
}
