package com.flores.nico.catholicprayers;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.flores.nico.database.Category;


public class EditCategoryActivity extends ActionBarActivity {
    private Context context;
    private Category category;
    private EditText categoryName;

    public void editCategoryButton (View view) {
        String name = categoryName.getText().toString();

        String message;
        if (name.isEmpty()) {
            message = getString(R.string.save_category_error);
        } else {
            category.setName(name);
            category.save();
            message = getString(R.string.save_category_success);
            setResult(RESULT_OK);
            finish();
        }
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_category);

        context = getApplicationContext();

        Intent intent = getIntent();
        long category_id = intent.getLongExtra(getString(R.string.category_id), 0);
        if (category_id == 0) {
            Toast.makeText(context, getString(R.string.no_category_id), Toast.LENGTH_SHORT).show();
            finish();
        }

        category = Category.findById(Category.class, category_id);
        categoryName = (EditText) findViewById(R.id.category_name_et_activity_edit_category);
        categoryName.setText(category.getName());
    }


    @Override
    public boolean onCreateOptionsMenu (Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.global, menu);
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

        return super.onOptionsItemSelected(item);
    }
}
