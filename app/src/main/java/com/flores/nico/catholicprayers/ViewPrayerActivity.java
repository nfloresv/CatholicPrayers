package com.flores.nico.catholicprayers;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.flores.nico.database.Prayer;


public class ViewPrayerActivity extends ActionBarActivity {
    private long prayer_id;
    private Context context;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_prayer);

        context = getApplicationContext();

        Intent intent = getIntent();
        prayer_id = intent.getLongExtra(getString(R.string.prayer_id), 0);
        if (prayer_id == 0) {
            Toast.makeText(context, getString(R.string.no_prayer_id), Toast.LENGTH_SHORT).show();
            finish();
        }

        Prayer prayer = Prayer.findById(Prayer.class, prayer_id);
        TextView prayerTitle = (TextView) findViewById(R.id.prayer_title_activity_view_prayer);
        TextView prayerText = (TextView) findViewById(R.id.prayer_text_activity_view_prayer);
        prayerTitle.setText(prayer.getTitle());
        prayerText.setText(prayer.getText());
    }


    @Override
    public boolean onCreateOptionsMenu (Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_view_prayer, menu);
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
        } else if (id == R.id.edit_prayer_menu_item) {
            Intent intent = new Intent(context, EditPrayerActivity.class);
            intent.putExtra(getString(R.string.prayer_id), prayer_id);

            int code = getResources().getInteger(R.integer.edit_prayer_activity_request_code);
            startActivityForResult(intent, code);

            return true;
        } else if (id == R.id.delete_prayer_menu_item) {
            Prayer prayer = Prayer.findById(Prayer.class, prayer_id);
            prayer.delete();
            setResult(RESULT_OK);
            finish();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult (int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        int code = getResources().getInteger(R.integer.edit_prayer_activity_request_code);

        if (requestCode == code) {
            if (resultCode == Activity.RESULT_OK) {
                setResult(RESULT_OK);
                finish();
            }
        }
    }
}
