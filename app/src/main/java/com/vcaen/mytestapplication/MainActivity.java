package com.vcaen.mytestapplication;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.vcaen.androidautohideheader.AutoHideHeaderLayout;


public class MainActivity extends ActionBarActivity {

    String[] mockValues = new String[40];

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        for (int i = 0; i < mockValues.length; i++) {
            mockValues[i] = "Mock Value " + i;
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AutoHideHeaderLayout view  = (AutoHideHeaderLayout) findViewById(R.id.autohideview);
        ListView listView = new ListView(this);

        listView.setAdapter(new ArrayAdapter<>(this, R.layout.list_item, R.id.info_text, mockValues));
        listView.setDividerHeight(10);
        //view.setHeader(R.layout.header);
        //view.setBodyView(listView);


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


}
