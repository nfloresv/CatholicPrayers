package com.flores.nico.catholicprayers;

import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.flores.nico.database.Category;


public class NewCategoryActivity extends ActionBarActivity {
    private Context context;
    private EditText categoryName;

    public void newCategoryButton (View view) {
        String name = categoryName.getText().toString();

        String message;
        if (name.isEmpty()) {
            message = getString(R.string.save_category_error);
        } else {
            Category category = new Category(name);
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
        setContentView(R.layout.activity_new_category);

        context = getApplicationContext();

        categoryName = (EditText) findViewById(R.id.category_name_et_activity_new_category);
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
