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


public class NewPrayerActivity extends ActionBarActivity {
    private Context context;
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
            message = getString(R.string.save_prayer_success);
            setResult(RESULT_OK);
            finish();
        }
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_prayer);

        context = getApplicationContext();

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
}
