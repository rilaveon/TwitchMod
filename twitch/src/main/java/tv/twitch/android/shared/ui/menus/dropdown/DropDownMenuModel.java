package tv.twitch.android.shared.ui.menus.dropdown;


import android.view.View;
import android.widget.ArrayAdapter;

import tv.twitch.android.shared.ui.menus.core.MenuModel;


public final class DropDownMenuModel<T> extends MenuModel {
    private int selectedOption; // TODO: __REMOVE_FINAL

    /* ... */

    public interface DropDownMenuItemSelection<T> {
        void onDropDownItemSelected(DropDownMenuModel<T> dropDownMenuModel, int i);
    }

    /* ... */

    public DropDownMenuModel(ArrayAdapter<T> arrayAdapter2, int i, String str, String str2, String str3, View.OnClickListener onClickListener, DropDownMenuItemSelection<T> dropDownMenuItemSelection2) {/* ... */}

    /* ... */

    public final int getSelectedOption() {
        return this.selectedOption;
    }


    public void setSelectedOption(int selectedOption) { // TODO: __INJECT_METHOD
        this.selectedOption = selectedOption;
    }
}
