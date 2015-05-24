package br.com.ifpb.solarsoft.activities;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import br.com.ifpb.solarsoft.R;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        IntentLauncher launcher = new IntentLauncher();
        launcher.start();
    }

    private class IntentLauncher extends Thread {

        @Override
        public void run() {
         try {
             // sleep
             Thread.sleep(3 * 1000);
         } catch (Exception e ) {
             e.printStackTrace();
         }
            Intent intent = new Intent(MainActivity.this, PacientesActivity.class);
            MainActivity.this.startActivity(intent);
            MainActivity.this.finish();
        }
    }
}
