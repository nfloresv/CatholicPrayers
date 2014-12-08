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

import com.flores.nico.database.Category;

import java.util.List;

public class CategoryListFragment extends Fragment implements AbsListView.OnItemClickListener {

    /**
     * The fragment's ListView/GridView.
     */
    private AbsListView mListView;
    /**
     * The Adapter which will be used to populate the ListView/GridView with
     * Views.
     */
    private ListAdapter mAdapter;
    private List<Category> categories;

    public static CategoryListFragment newInstance () {
        return new CategoryListFragment();
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public CategoryListFragment () {
        categories = Category.listAll(Category.class);
    }

    @Override
    public void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        mAdapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_list_item_1, android.R.id.text1, categories);
    }

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_fragment_category, container, false);

        // Set the adapter
        mListView = (AbsListView) view.findViewById(R.id.categories_list_fragment_category);
        mListView.setAdapter(mAdapter);

        // Set OnItemClickListener so we can be notified on item clicks
        mListView.setOnItemClickListener(this);

        if (categories.size() == 0) {
            setEmptyText(getText(R.string.empty_category_list));
        }

        return view;
    }

    @Override
    public void onCreateOptionsMenu (Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_category_list_fragment, menu);
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.new_category_menu_item) {
            Intent intent = new Intent(getActivity(), NewCategoryActivity.class);
            int code = getResources().getInteger(R.integer.new_category_activity_request_code);
            startActivityForResult(intent, code);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult (int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        android.support.v4.app.FragmentManager fragmentManager = getFragmentManager();
        if (requestCode == getResources().getInteger(R.integer.new_category_activity_request_code)) {
            if (resultCode == Activity.RESULT_OK) {
                fragmentManager.beginTransaction()
                        .replace(R.id.container, CategoryListFragment.newInstance())
                        .commit();
            }
        }
        else if (requestCode == getResources().getInteger(R.integer.view_category_activity_request_code)) {
            if (resultCode == Activity.RESULT_OK) {
                fragmentManager.beginTransaction()
                        .replace(R.id.container, CategoryListFragment.newInstance())
                        .commit();
            }
        }
    }

    @Override
    public void onItemClick (AdapterView<?> parent, View view, int position, long id) {
        Category category = categories.get(position);
        Context context = getActivity().getApplicationContext();

        Intent intent = new Intent(context, ViewCategoryActivity.class);
        intent.putExtra(getString(R.string.category_id), category.getId());

        int code = getResources().getInteger(R.integer.view_category_activity_request_code);
        startActivityForResult(intent, code);
    }

    /**
     * The default content for this Fragment has a TextView that is shown when
     * the list is empty. If you would like to change the text, call this method
     * to supply the text it should use.
     */
    public void setEmptyText (CharSequence emptyText) {
        View emptyView = mListView.getEmptyView();

        if (emptyView instanceof TextView) {
            ((TextView) emptyView).setText(emptyText);
        }
    }

}
