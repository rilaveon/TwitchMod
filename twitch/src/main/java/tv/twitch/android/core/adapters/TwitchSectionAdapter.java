package tv.twitch.android.core.adapters;


import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public final class TwitchSectionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    /* ... */

    public final List<RecyclerAdapterSection> getSections() {
        /* ... */

        return null;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    /* ... */
}
