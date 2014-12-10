package com.flores.nico.catholicprayers;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.flores.nico.database.Prayer;

import java.util.List;


public class PrayerListFragment extends Fragment implements AbsListView.OnItemClickListener {

    /**
     * The fragment's ListView/GridView.
     */
    private AbsListView mListView;
    /**
     * The Adapter which will be used to populate the ListView/GridView with
     * Views.
     */
    private ListAdapter mAdapter;
    private List<Prayer> prayers;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public PrayerListFragment () {
        prayers = Prayer.listAll(Prayer.class);
    }

    public static PrayerListFragment newInstance () {
        return new PrayerListFragment();
    }

    @Override
    public void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        mAdapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_list_item_1, android.R.id.text1, prayers);
    }

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_fragment_prayer, container, false);

        // Set the adapter
        mListView = (AbsListView) view.findViewById(R.id.prayers_list_fragment_prayer);
        mListView.setAdapter(mAdapter);

        // Set OnItemClickListener so we can be notified on item clicks
        mListView.setOnItemClickListener(this);

        if (prayers.size() == 0) {
            setEmptyText(getText(R.string.empty_prayer_list));
        }

        return view;
    }

    @Override
    public void onCreateOptionsMenu (Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_prayer_list_fragment, menu);
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.new_prayer_menu_item) {
            Intent intent = new Intent(getActivity(), NewPrayerActivity.class);
            int code = getResources().getInteger(R.integer.new_prayer_activity_request_code);
            startActivityForResult(intent, code);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult (int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == getResources().getInteger(R.integer.new_prayer_activity_request_code)) {
            if (resultCode == Activity.RESULT_OK) {
                android.support.v4.app.FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.container, PrayerListFragment.newInstance())
                        .commit();
            }
        } else if (requestCode == getResources().getInteger(R.integer.view_prayer_activity_request_code)) {
            if (resultCode == Activity.RESULT_OK) {
                android.support.v4.app.FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.container, PrayerListFragment.newInstance())
                        .commit();
            }
        }
    }

    @Override
    public void onItemClick (AdapterView<?> parent, View view, int position, long id) {
        Prayer pray = prayers.get(position);
        Context context = getActivity().getApplicationContext();

        Intent intent = new Intent(context, ViewPrayerActivity.class);
        intent.putExtra(getString(R.string.prayer_id), pray.getId());

        int code = getResources().getInteger(R.integer.view_prayer_activity_request_code);
        startActivityForResult(intent, code);
    }

    public void setEmptyText (CharSequence emptyText) {
        View emptyView = mListView.getEmptyView();

        if (emptyView instanceof TextView) {
            ((TextView) emptyView).setText(emptyText);
        }
    }

}
