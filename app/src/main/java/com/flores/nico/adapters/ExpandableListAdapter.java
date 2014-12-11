package com.flores.nico.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.flores.nico.catholicprayers.R;
import com.flores.nico.database.Category;
import com.flores.nico.database.Prayer;

import java.util.HashMap;
import java.util.List;

/**
 * Created by nicoflores on 10-12-14.
 */
public class ExpandableListAdapter extends BaseExpandableListAdapter {
    private Context context;
    private List<Category> categories;
    private HashMap<Category, List<Prayer>> prayers;

    public ExpandableListAdapter (Context context, List<Category> categories, HashMap<Category, List<Prayer>> prayers) {
        this.context = context;
        this.categories = categories;
        this.prayers = prayers;
    }

    @Override
    public int getGroupCount () {
        return categories.size();
    }

    @Override
    public int getChildrenCount (int groupPosition) {
        return prayers.get(categories.get(groupPosition)).size();
    }

    @Override
    public Category getGroup (int groupPosition) {
        return categories.get(groupPosition);
    }

    @Override
    public Prayer getChild (int groupPosition, int childPosition) {
        return prayers.get(categories.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId (int groupPosition) {
        return categories.get(groupPosition).getId();
    }

    @Override
    public long getChildId (int groupPosition, int childPosition) {
        return prayers.get(categories.get(groupPosition)).get(childPosition).getId();
    }

    @Override
    public boolean hasStableIds () {
        return false;
    }

    @Override
    public View getGroupView (int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context
                    .LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.expandable_list_group, null);
        }
        Category category = categories.get(groupPosition);
        TextView groupHeader = (TextView) convertView.findViewById(R.id.expandable_list_header);
        groupHeader.setText(category.getName());
        return convertView;
    }

    @Override
    public View getChildView (int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context
                    .LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.expandable_list_item, null);
        }
        Category category = categories.get(groupPosition);
        Prayer prayer = prayers.get(category).get(childPosition);
        TextView item = (TextView) convertView.findViewById(R.id.expandable_list_child);
        item.setText(prayer.getTitle());
        return convertView;
    }

    @Override
    public boolean isChildSelectable (int groupPosition, int childPosition) {
        return true;
    }
}
