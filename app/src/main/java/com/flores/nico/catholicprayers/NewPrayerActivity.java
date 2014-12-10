package com.flores.nico.catholicprayers;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.flores.nico.database.Category;
import com.flores.nico.database.Prayer;
import com.flores.nico.database.PrayerCategory;

import java.util.List;


public class NewPrayerActivity extends ActionBarActivity implements DialogInterface
        .OnMultiChoiceClickListener, DialogInterface.OnClickListener {
    private Context context;
    private CharSequence[] categories;
    private boolean[] categoriesSelected;
    private EditText prayerTitle;
    private EditText prayerText;

    public void newPrayerButton (View view) {
        String title = prayerTitle.getText().toString();
        String text = prayerText.getText().toString();

        String message;
        if (title.isEmpty() || text.isEmpty()) {
            message = getString(R.string.save_prayer_error);
        } else {
            Prayer prayer = new Prayer(title, text);
            prayer.save();
            generateRelationPrayerCategory(prayer);
            message = getString(R.string.save_prayer_success);
            setResult(RESULT_OK);
            finish();
        }
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public void newPrayerCategory (View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.prayer_category_selection);
        builder.setMultiChoiceItems(categories, categoriesSelected, this);
        builder.setPositiveButton(R.string.btn_acept_dialog, this);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void generateRelationPrayerCategory (Prayer prayer) {
        List<Category> categoryList = Category.listAll(Category.class);
        for (int i = 0; i < categoryList.size(); i++) {
            List<PrayerCategory> prayerCategories = PrayerCategory.find(PrayerCategory.class,
                    "prayer = ? and category = ?", String.valueOf(prayer.getId()),
                    String.valueOf(categoryList.get(i).getId()));
            if (categoriesSelected[i]) {
                if (prayerCategories.size() == 0) {
                    PrayerCategory prayerCategory = new PrayerCategory(categoryList.get(i), prayer);
                    prayerCategory.save();
                }
            } else {
                for (PrayerCategory prayerCategory : prayerCategories) {
                    prayerCategory.delete();
                }
            }
        }
    }

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_prayer);

        context = getApplicationContext();

        List<Category> categoryList = Category.listAll(Category.class);
        categories = new CharSequence[categoryList.size()];
        for (int i = 0; i < categoryList.size(); i++) {
            categories[i] = categoryList.get(i).toString();
        }
        categoriesSelected = new boolean[categories.length];

        prayerTitle = (EditText) findViewById(R.id.prayer_title_et_activity_new_prayer);
        prayerText = (EditText) findViewById(R.id.prayer_text_et_activity_new_prayer);
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

    @Override
    public void onClick (DialogInterface dialog, int which) {
        String message = "";
        for (int i = 0; i < categoriesSelected.length; i++) {
            if (categoriesSelected[i]) {
                message += categories[i].toString() + ", ";
            }
        }
        if (message.length() > 0) {
            message = message.substring(0, message.length() - 2);
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick (DialogInterface dialog, int which, boolean isChecked) {
        categoriesSelected[which] = isChecked;
    }
}
