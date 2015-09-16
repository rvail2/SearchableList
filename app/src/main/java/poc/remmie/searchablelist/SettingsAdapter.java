package poc.remmie.searchablelist;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class SettingsAdapter extends RecyclerView.Adapter<SettingsAdapter.TextViewHolder> {

    private int TITLE = 0;
    private int SUBITEM = 1;

    private List<MainActivity.FictionSettings> mFictionSettings;

    public SettingsAdapter(final List<MainActivity.FictionSettings> settings) {
        mFictionSettings = settings;
    }

    @Override
    public SettingsAdapter.TextViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        final TextViewHolder holder;
        if (viewType == TITLE) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.view_holder_title, viewGroup, false);
            holder = new TitleViewHolder(view);
        } else {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.view_holder_subitem, viewGroup, false);
            holder = new SubItemViewHolder(view);
        }

        return holder;
    }

    @Override
    public int getItemViewType(int position) {
        return mFictionSettings.get(position).isSectionTitle() ? TITLE : SUBITEM;
    }

    @Override
    public void onBindViewHolder(SettingsAdapter.TextViewHolder viewHolder, int i) {
        final MainActivity.FictionSettings setting = mFictionSettings.get(i);
        viewHolder.setText(setting.getLabel());
    }

    @Override
    public int getItemCount() {
        return mFictionSettings.size();
    }

    public static abstract class TextViewHolder extends RecyclerView.ViewHolder {
        public TextViewHolder(View itemView) {
            super(itemView);
        }

        public abstract void setText(String text);
    }

    private static class TitleViewHolder extends TextViewHolder {
        private TextView title;

        public TitleViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.label);
        }

        @Override
        public void setText(String text) {
            title.setText(text);
        }
    }

    private static class SubItemViewHolder extends TextViewHolder {
        private TextView subitem;

        public SubItemViewHolder(View itemView) {
            super(itemView);
            subitem = (TextView) itemView.findViewById(R.id.subitem);
        }

        @Override
        public void setText(String text) {
            subitem.setText(text);
        }
    }
}
