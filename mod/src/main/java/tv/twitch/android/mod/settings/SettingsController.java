package tv.twitch.android.mod.settings;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Process;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;

import androidx.fragment.app.FragmentActivity;

import java.util.Arrays;
import java.util.List;

import tv.twitch.android.mod.bridges.IDPub;
import tv.twitch.android.mod.bridges.LoaderLS;
import tv.twitch.android.mod.models.settings.UserMessagesFiltering;
import tv.twitch.android.mod.models.settings.EmoteSize;
import tv.twitch.android.mod.models.settings.ExoPlayerSpeed;
import tv.twitch.android.mod.models.settings.Gifs;
import tv.twitch.android.mod.models.settings.MiniPlayerSize;
import tv.twitch.android.mod.models.settings.PlayerImpl;
import tv.twitch.android.mod.models.PreferenceItem;
import tv.twitch.android.mod.utils.Helper;
import tv.twitch.android.mod.utils.Logger;
import tv.twitch.android.settings.SettingsActivity;
import tv.twitch.android.shared.ui.menus.j;
import tv.twitch.android.shared.ui.menus.l.b;
import tv.twitch.android.shared.ui.menus.m.a;

import static tv.twitch.android.mod.settings.PreferenceManager.DEV;


public class SettingsController {
    private static final String TELEGRAM_URL = "https://t.me/pubTw";
    private static final String GITHUB_URL = "https://github.com/nopbreak/TwitchMod";




    public static final class ToggleMenuChangeListener implements j { // TODO: __INJECT_CLASS
        private final FragmentActivity mFragmentActivity;

        public ToggleMenuChangeListener(FragmentActivity fragmentActivity) {
            mFragmentActivity = fragmentActivity;
        }

        @Override
        public void a(tv.twitch.android.shared.ui.menus.s.b item, boolean isChecked) {
            SettingsController.OnMenuItemToggled(mFragmentActivity, item, isChecked);
        }

        @Override
        public void b(tv.twitch.android.shared.ui.menus.k.b bVar) {}
    }


    public static final class onMenuItemSelected implements a.a1<PreferenceItem> {
        final ArrayAdapter<PreferenceItem> mAdapter;

        onMenuItemSelected(ArrayAdapter<PreferenceItem> arrayAdapter) {
            this.mAdapter = arrayAdapter;
        }

        @Override
        public void a(a<PreferenceItem> menuModel, int itemPos, boolean z) {
            PreferenceItem preference = this.mAdapter.getItem(itemPos);
            if (preference == null)
                return;

            LoaderLS.getPrefManagerInstance().updateString(preference.getKey(), preference.getValue());
        }
    }

    public static void OnMenuItemToggled(FragmentActivity fragmentActivity, tv.twitch.android.shared.ui.menus.s.b menuItem, boolean isChecked) {
        if (menuItem == null) {
            Logger.error("menuItem is null");
            return;
        }

        j.a prefType = menuItem.s();
        if (prefType == null) {
            Logger.error("prefType is null");
            return;
        }

        final String preferenceKey = prefType.getPreferenceKey();
        if (TextUtils.isEmpty(preferenceKey)) {
            Logger.warning("preferenceKey is null. Object=" + prefType.toString());
            return;
        }

        LoaderLS.getPrefManagerInstance().updateBoolean(preferenceKey, isChecked);
        restartIfNeed(fragmentActivity, preferenceKey);
    }

    private static void restartIfNeed(FragmentActivity fragmentActivity, String preferenceKey) {
        switch (preferenceKey) {
            case PreferenceManager.HIDE_DISCOVER:
            case PreferenceManager.HIDE_ESPORTS:
                new AlertDialog.Builder(fragmentActivity).setMessage("Refresh and restart?").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        PendingIntent pendingIntent = PendingIntent.getActivity(LoaderLS.getInstance(), 0, new Intent(LoaderLS.getInstance(), SettingsActivity.class), PendingIntent.FLAG_CANCEL_CURRENT);
                        ((AlarmManager) LoaderLS.getInstance().getSystemService(Context.ALARM_SERVICE)).setExact(AlarmManager.RTC, 1000, pendingIntent);
                        Process.killProcess(Process.myPid());

                    }
                }).setNegativeButton("No", null).create().show();
        }
    }

    public static class MenuFactory {
        private static tv.twitch.android.shared.ui.menus.l.b getInfoMenu(String title, String desc, View.OnClickListener listener) {
            return new tv.twitch.android.shared.ui.menus.o.a(title, desc, null, null, null, null, listener);
        }

        private static tv.twitch.android.shared.ui.menus.l.b getInfoMenu(String title) {
            return new tv.twitch.android.shared.ui.menus.o.a(title, null, null, null, null, null, null, 0, null);
        }

        private static tv.twitch.android.shared.ui.menus.l.b getToggleMenu(j.a controller, IDPub idPub, boolean state) {
            return new tv.twitch.android.shared.ui.menus.s.b(idPub.getString(controller.getNameKey()), idPub.getString(controller.getDescriptionKey()), null, state, true, null, null, false, null, null, null, controller, null);
        }

        private static tv.twitch.android.shared.ui.menus.l.b getDropDownMenu(j.a controller, Context context, IDPub idPub, PreferenceItem[] items, PreferenceItem state) {
            ArrayAdapter<PreferenceItem> adapter = new ArrayAdapter<>(context, tv.twitch.android.settings.d.twitch_spinner_dropdown_item, items);
            return new tv.twitch.android.shared.ui.menus.m.a<>(adapter, Arrays.asList(items).indexOf(state), idPub.getString(controller.getNameKey()), idPub.getString(controller.getDescriptionKey()), null, null, new onMenuItemSelected(adapter));
        }
    }

    public static j getMenuListener(FragmentActivity activity) {
        return new ToggleMenuChangeListener(activity);
    }

    public static void initialize(final Context context, List<b> items) {
        items.clear();

        final PreferenceManager preferenceManager = LoaderLS.getPrefManagerInstance();
        IDPub idPub = LoaderLS.getIDPubInstance();

        items.add(MenuFactory.getInfoMenu(idPub.getString("mod_category_settings_chat_bttv")));
        items.add(MenuFactory.getToggleMenu(j.a.BttvEmotes, idPub, preferenceManager.isBttvOn()));
        items.add(MenuFactory.getToggleMenu(j.a.BttvEmotesPicker, idPub, preferenceManager.isEmotePickerOn()));
        items.add(MenuFactory.getDropDownMenu(j.a.Gifs, context, idPub, Gifs.values(), preferenceManager.getGifsStrategy()));
        items.add(MenuFactory.getDropDownMenu(j.a.EmoteSize, context, idPub, EmoteSize.values(), preferenceManager.getEmoteSize()));

        items.add(MenuFactory.getInfoMenu(idPub.getString("mod_category_settings_chat_category")));
        items.add(MenuFactory.getToggleMenu(j.a.FloatingChat, idPub, preferenceManager.isFloatingChatEnabled()));
        items.add(MenuFactory.getToggleMenu(j.a.Timestamps, idPub, preferenceManager.isMessageTimestampOn()));
        items.add(MenuFactory.getToggleMenu(j.a.EmotePickerView, idPub, preferenceManager.isOldEmotePicker()));

        items.add(MenuFactory.getInfoMenu(idPub.getString("mod_category_settings_player_category")));
        items.add(MenuFactory.getToggleMenu(j.a.AutoPlay, idPub, preferenceManager.isDisableAutoplay()));
        items.add(MenuFactory.getToggleMenu(j.a.VideoDebugPanel, idPub, preferenceManager.isShowVideoDebugPanel()));
        items.add(MenuFactory.getToggleMenu(j.a.FollowView, idPub, preferenceManager.isOldFollowButton()));
        items.add(MenuFactory.getDropDownMenu(j.a.PlayerImpl, context, idPub, PlayerImpl.values(), preferenceManager.getPlayerImplementation()));
        items.add(MenuFactory.getDropDownMenu(j.a.MiniplayerSize, context, idPub, MiniPlayerSize.values(), preferenceManager.getMiniPlayerSize()));
        items.add(MenuFactory.getDropDownMenu(j.a.ExoplayerSpeed, context, idPub, ExoPlayerSpeed.values(), preferenceManager.getExoplayerSpeed()));

        items.add(MenuFactory.getInfoMenu(idPub.getString("mod_category_settings_swipe")));
        items.add(MenuFactory.getToggleMenu(j.a.VolumeSwipe, idPub, preferenceManager.isVolumeSwipeEnabled()));
        items.add(MenuFactory.getToggleMenu(j.a.BrightnessSwipe, idPub, preferenceManager.isBrightnessSwipeEnabled()));

        items.add(MenuFactory.getInfoMenu(idPub.getString("mod_category_chat_filtering")));
        items.add(MenuFactory.getDropDownMenu(j.a.FilterLevel, context, idPub, UserMessagesFiltering.values(), preferenceManager.getChatFiltering()));
        items.add(MenuFactory.getToggleMenu(j.a.FilterSystem, idPub, preferenceManager.isIgnoreSystemMessages()));

        items.add(MenuFactory.getInfoMenu(idPub.getString("mod_category_settings_patch")));
        items.add(MenuFactory.getToggleMenu(j.a.HideRecommendedStreams, idPub, preferenceManager.isDisableRecommendations()));
        items.add(MenuFactory.getToggleMenu(j.a.HideResumeWatchingStreams, idPub, preferenceManager.isDisableRecentWatching()));
        items.add(MenuFactory.getToggleMenu(j.a.HideFollowedGames, idPub, preferenceManager.isDisableFollowedGames()));
        items.add(MenuFactory.getToggleMenu(j.a.HideDiscoverTab, idPub, preferenceManager.isHideDiscoverTab()));
        items.add(MenuFactory.getToggleMenu(j.a.HideEsportsTab, idPub, preferenceManager.isHideEsportsTab()));
        items.add(MenuFactory.getToggleMenu(j.a.RecentSearch, idPub,  preferenceManager.isDisableRecentSearch()));
        if (preferenceManager.isDevModeOn()) {
            items.add(MenuFactory.getToggleMenu(j.a.Interceptor, idPub, preferenceManager.isInterceptorOn()));
        }

        items.add(MenuFactory.getInfoMenu(idPub.getString("mod_category_info")));

        items.add(MenuFactory.getInfoMenu(LoaderLS.VERSION, LoaderLS.BUILD, new View.OnClickListener() {
            private int clicked = 0;
            @Override
            public void onClick(View v) {
                clicked++;
                if (clicked == 5) {
                    if (preferenceManager.isDevModeOn()) {
                        preferenceManager.updateBoolean(DEV, false);
                        Helper.showToast("Dev mode off!");
                    } else {
                        preferenceManager.updateBoolean(DEV, true);
                        Helper.showToast("Dev mode on!");
                    }
                    clicked = 0;
                }
            }
        }));
        items.add(MenuFactory.getInfoMenu(idPub.getString("mod_info_open_telegram"), null, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Helper.openUrl(TELEGRAM_URL);
            }
        }));
        items.add(MenuFactory.getInfoMenu(idPub.getString("mod_info_open_github"), null, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Helper.openUrl(GITHUB_URL);
            }
        }));
    }
}
