package com.flores.nico.catholicprayers;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.flores.nico.database.Prayer;


public class EditPrayerActivity extends ActionBarActivity {
    private Context context;
    private Prayer prayer;
    private EditText prayerTitle;
    private EditText prayerText;

    public void editPrayerButton (View view) {
        String title = prayerTitle.getText().toString();
        String text = prayerText.getText().toString();

        String message;
        if (title.isEmpty() || text.isEmpty()) {
            message = getString(R.string.save_prayer_error);
        } else {
            prayer.setTitle(title);
            prayer.setText(text);
            prayer.save();
            message = getString(R.string.save_prayer_success);
            setResult(RESULT_OK);
            finish();
        }
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_prayer);

        context = getApplicationContext();

        Intent intent = getIntent();
        long prayer_id = intent.getLongExtra(getString(R.string.prayer_id), 0);
        if (prayer_id == 0) {
            Toast.makeText(context, getString(R.string.no_prayer_id), Toast.LENGTH_SHORT).show();
            finish();
        }

        prayer = Prayer.findById(Prayer.class, prayer_id);
        prayerTitle = (EditText) findViewById(R.id.prayer_title_et_activity_edit_prayer);
        prayerText = (EditText) findViewById(R.id.prayer_text_et_activity_edit_prayer);
        prayerTitle.setText(prayer.getTitle());
        prayerText.setText(prayer.getText());
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
