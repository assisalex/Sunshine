package com.example.android.sunshine;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
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


        /*
        These two need to be declared outside the try/catch
        so that they can be closed in the finally block
         */
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        // Will contain the raw Json response as a String
        String forecastJsonStr = null;

        try{
            /*
            Construct the URL for the OpenWeatherMap query
            Possible parameters are available at OWM's forecast API page, at
            http://openweathermap.org/API#forecast
             */
            URL url = new URL("http://api.openweathermap.org/data/2.5/forecast/daily?q=Governador%20Valadares&mode=json&units=metric&cnt=7");

            // Create the request to Open
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // Read the input stream into a String
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null){
                // Nothing to do
                forecastJsonStr = null;
            }

            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while((line = reader.readLine()) != null){
                /*
                Since it' JSON, adding a newlin isn't necessary (it won't affect parsing)
                But ir does make debuggin a *lot* easier if you print out the completed
                buffer for debuging
                 */
                buffer.append(line + "\n");
            }
            if (buffer.length() == 0) {
                // Stream was empty. No point in parsing
                forecastJsonStr = null;
            }

            forecastJsonStr = buffer.toString();

        }catch (IOException e){
            Log.e("MainActivity:onCreate", "Erro",e);
            // if the code didn't sucessfully get the weather data, there' no point in attempting
            // to parse it.
            forecastJsonStr = null;
        }finally {
            if (urlConnection != null){
                urlConnection.disconnect();
            }

            if (reader != null){
                try{
                    reader.close();
                    }catch (final IOException e){
                        Log.e("MainActivity", "Error closing stream", e);
                }
            }
        }
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
