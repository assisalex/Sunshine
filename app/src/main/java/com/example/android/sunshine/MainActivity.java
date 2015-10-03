package com.example.android.sunshine;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

//public class MainActivity extends AppCompatActivity {
public class MainActivity extends AppCompatActivity {

    private ArrayList<String> weekForecast;
    private ArrayAdapter<String> adapterForecast;
    private ListView lvForecast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        weekForecast = new ArrayList<String>();
        weekForecast.add("Today - Sunny - 33 / 21");
        weekForecast.add("Saturday - Cloudy - 36 / 19");
        weekForecast.add("Sunday - Rainy - 35 / 21");
        weekForecast.add("Monday - Rainy - 28 / 21");
        weekForecast.add("Tuesday - Cloudy - 28 / 19");
        weekForecast.add("Wednesday - Sunny - 31 / 19");
        weekForecast.add("Thursday - Cloudy - 31 / 20");

        adapterForecast = new ArrayAdapter<String>(
                // The current context (this fragment's parent activity)
                getBaseContext(),
                // ID of list item layout
                R.layout.list_item_forecast,
                // ID of the textview to populate
                R.id.list_item_forecast_textview,
                // forecast data
                weekForecast
        );

        lvForecast = (ListView) findViewById(R.id.listview_forecast);
        lvForecast.setAdapter(adapterForecast);


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
