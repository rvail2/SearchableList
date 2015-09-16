package poc.remmie.searchablelist;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputFilter;
import android.text.Spanned;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    private List<FictionSettings> mCompleteSettingList;
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mCompleteSettingList = createMythicalListOfSettings();
        final SettingsAdapter adapter = new SettingsAdapter(mCompleteSettingList);

        mRecyclerView = (RecyclerView) findViewById(R.id.rvSettings);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setAdapter(adapter);

        final InputFilter searchInputFilter = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                filterSettings(source.toString());
                return source;
            }
        };

        final EditText searchInputWidget = (EditText) findViewById(R.id.searchTextInput);
        searchInputWidget.setFilters(new InputFilter[]{searchInputFilter});
    }


    private void filterSettings(String source) {
        for (FictionSettings setting : mCompleteSettingList) {
            setting.setShow(false);
        }

        for (int i = 0; i < mCompleteSettingList.size(); i++) {
            final FictionSettings fictionSettings = mCompleteSettingList.get(i);

            if (fictionSettings.getLabel().toUpperCase().contains(source.toUpperCase())) {
                fictionSettings.setShow(true);
                if (fictionSettings.getCategory() != null) {
                    fictionSettings.getCategory().setShow(true);
                }
            }
        }


        final List<FictionSettings> validSettings = new ArrayList<>();
        for (FictionSettings setting : mCompleteSettingList) {
            if (setting.isVisible()) {
                validSettings.add(setting);
            }
        }

        mRecyclerView.setAdapter(new SettingsAdapter(validSettings));
    }


    private List<FictionSettings> createMythicalListOfSettings() {

        final List<FictionSettings> settings = new ArrayList<>();
        final FictionSettings colors = new FictionSettings("Colors", null);
        settings.add(colors);
        settings.add(new FictionSettings("Text Color", colors));
        settings.add(new FictionSettings("Background Color", colors));
        final FictionSettings gestures = new FictionSettings("Gestures", null);
        settings.add(gestures);
        settings.add(new FictionSettings("Swipe Right", gestures));

        return settings;
    }


    public static class FictionSettings {
        private String mLabel;
        private FictionSettings mCategory;
        private boolean isVisible;

        public FictionSettings(String label, FictionSettings category) {
            mLabel = label;
            mCategory = category;
            isVisible = true;
        }

        public String getLabel() {
            return mLabel;
        }

        public boolean isSectionTitle() {
            return mCategory == null;
        }

        public FictionSettings getCategory() {
            return mCategory;
        }

        public void setShow(boolean display) {
            isVisible = display;
        }

        public boolean isVisible() {
            return isVisible;
        }
    }
}
