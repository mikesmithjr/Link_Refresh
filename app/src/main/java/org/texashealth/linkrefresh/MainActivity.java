package org.texashealth.linkrefresh;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity implements View.OnClickListener{

    Button dlyRefreshBtn;
    Button wklyRefreshBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        dlyRefreshBtn = (Button)findViewById(R.id.dlyBtn);
        wklyRefreshBtn = (Button)findViewById(R.id.wklyBtn);
        dlyRefreshBtn.setOnClickListener(this);
        wklyRefreshBtn.setOnClickListener(this);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
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
    public void onClick(View v) {

        if(v.equals(this.dlyRefreshBtn)){
            Log.i("Main Activity", "Daily Button Pressed");
            Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                    "mailto", "michaelsmith5@texashealth.org", null));
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Daily Refresh");
            emailIntent.putExtra(Intent.EXTRA_TEXT, "refresh_dly");
            startActivity(emailIntent);
           /* try {
                GMailSender sender = new GMailSender("snuffyms@gmail.com", "ladybugn19");
                Log.i("Main Activity", "Building Gmail User for DLY");
                sender.sendMail("Daily Refresh",
                        "dly_refresh",
                        "michaelsmith5@texashealth.org",
                        "michaelsmith5@texashealth.org");
            } catch (Exception e) {
                Log.e("SendMail", e.getMessage(), e);
                Log.i("Main Activity", "Building Gmail User for DLY EXCEPTION");

            }*/

        }else if(v.equals(this.wklyRefreshBtn)){
            Log.i("Main Activity", "Weekly Button Pressed");

            Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                    "mailto", "michaelsmith5@texashealth.org", null));
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Weekly Refresh");
            emailIntent.putExtra(Intent.EXTRA_TEXT, "refresh_wky");
            startActivity(emailIntent);
            /*try {
                GMailSender sender = new GMailSender("snuffyms@gmail.com", "ladybugn19");
                Log.i("Main Activity", "Building Gmail User for WKY");
                sender.sendMail("Weekly Refresh",
                        "wky_refresh",
                        "michaelsmith5@texashealth.org",
                        "michaelsmith5@texashealth.org");
            } catch (Exception e) {
                Log.e("SendMail", e.getMessage(), e);
                Log.i("Main Activity", "Building Gmail User for WKY EXCEPTION");
            }*/
        }

    }
}
