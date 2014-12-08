package com.flores.nico.catholicprayers;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.flores.nico.database.Category;

public class ViewCategoryActivity extends ActionBarActivity {
    private long category_id;
    private Context context;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_category);

        context = getApplicationContext();

        Intent intent = getIntent();
        category_id = intent.getLongExtra(getString(R.string.category_id), 0);
        if (category_id == 0) {
            Toast.makeText(context, getString(R.string.no_category_id), Toast.LENGTH_SHORT).show();
            finish();
        }

        Category category = Category.findById(Category.class, category_id);
        TextView categoryName = (TextView) findViewById(R.id.category_name_activity_view_category);
        categoryName.setText(category.getName());
    }


    @Override
    public boolean onCreateOptionsMenu (Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_view_category, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        else if (id == R.id.edit_prayer_menu_item) {
            Intent intent = new Intent(context, EditCategoryActivity.class);
            intent.putExtra(getString(R.string.category_id), category_id);

            int code = getResources().getInteger(R.integer.edit_category_activity_request_code);
            startActivityForResult(intent, code);

            return true;
        }
        else if (id == R.id.delete_prayer_menu_item) {
            Category category = Category.findById(Category.class, category_id);
            category.delete();
            setResult(RESULT_OK);
            finish();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult (int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        int code = getResources().getInteger(R.integer.edit_category_activity_request_code);

        if (requestCode == code) {
            if (resultCode == Activity.RESULT_OK) {
                setResult(RESULT_OK);
                finish();
            }
        }
    }
}
