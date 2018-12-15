package com.alvaro.sergio.smov_yamba;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import com.alvaro.sergio.smov_yamba.RefreshService;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int rows;
        switch (item.getItemId()) {
            case R.id.action_purge:
                rows = getContentResolver().delete(SupportServices.CONTENT_URI,
                        null, null);
                Toast.makeText(this, rows + " filas de la base de datos borradas",
                        Toast.LENGTH_LONG).show();
                return true;
            case R.id.action_settings:
                startActivity(new Intent(this, SettingsActivity.class));
                return true;
            case R.id.action_twet:
                startActivity(new Intent(this, StatusActivity.class));
                return true;
            case R.id.itemServiceStart:
                startService(new Intent(this, RefreshService.class));
                return true;
            case R.id.itemServiceStop:
                stopService(new Intent(this, RefreshService.class));
                return true;
            case R.id.itemServiceHomeTimeline:  /*Modificacion */
                rows = getContentResolver().delete(SupportServices.CONTENT_URI,
                        null, null);
                RefreshService.userTimeline = false;
                return true;
            case R.id.itemServiceTwetsTimeline:
                rows = getContentResolver().delete(SupportServices.CONTENT_URI,
                        null, null);
                RefreshService.userTimeline = true;
                return true;
//            --------------------------------------------------------
            default:
                return false;
        }
    }
}
