package com.flores.nico.catholicprayers;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.flores.nico.adapters.ExpandableListAdapter;
import com.flores.nico.database.Category;
import com.flores.nico.database.Prayer;
import com.flores.nico.database.PrayerCategory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CategoryPrayersFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CategoryPrayersFragment extends Fragment {
    private Context context;
    private ExpandableListAdapter adapter;
    private ExpandableListView listView;
    private List<Category> categories;
    private HashMap<Category, List<Prayer>> prayers;

    public CategoryPrayersFragment () {
    }

    public static CategoryPrayersFragment newInstance () {
        return new CategoryPrayersFragment();
    }

    @Override
    public void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity().getApplicationContext();
        categories = Category.listAll(Category.class);
        prayers = new HashMap<>();
        for (Category category : categories) {
            List<Prayer> prayerList = new ArrayList<>();
            List<PrayerCategory> prayerCategories = PrayerCategory.find(PrayerCategory.class,
                    "category = ?", String.valueOf(category.getId()));
            for (PrayerCategory prayerCategory : prayerCategories) {
                prayerList.add(prayerCategory.getPrayer());
            }
            prayers.put(category, prayerList);
        }
        adapter = new ExpandableListAdapter(context, categories, prayers);
    }

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_category_prayers, container, false);
        listView = (ExpandableListView) layout.findViewById(R.id
                .category_prayers_el_fragment_category_prayers);
        listView.setAdapter(adapter);
        return layout;
    }
    //http://www.androidhive.info/2013/07/android-expandable-list-view-tutorial/

}
