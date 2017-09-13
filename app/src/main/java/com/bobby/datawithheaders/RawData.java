package com.bobby.datawithheaders;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by bobby on 9/12/17.
 */

public class RawData extends AsyncTask<String, Void, String> {
    private static final String TAG = "RawData";
    private String matchUp;
    private String awayOdds;
    private String homeOdds;

    private double amAwayOdds;
    private double amHomeOdds;

    @Override
    protected String doInBackground(String... urls) {
        String result = "";
        URL url;
        HttpURLConnection connection;
        matchUp = urls[1];

        try {
            url = new URL(urls[0]);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("x-api-key", "Ax5Q36JSfPaoZvOzQs5Bx370A6vC7UTE6gaFOLqt");
            InputStream inputStream = connection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            int data = inputStreamReader.read();

            while (data != -1){
                char current = (char) data;
                result += current;
                data = inputStreamReader.read();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        Log.i(TAG, s);
        try {
            JSONObject jsonObject = new JSONObject(s);
            String data = jsonObject.getString("data");
            Log.i(TAG, data);
            String events = jsonObject.getJSONObject("data").getString("events");
            Log.i(TAG, events);
            String object = jsonObject.getJSONObject("data").getJSONObject("events")
                    .getString("San Francisco 49ers_Seattle Seahawks");
            Log.i(TAG, object);
            String odds = jsonObject.getJSONObject("data").getJSONObject("events")
                    .getJSONObject(matchUp).getJSONObject("sites")
                    .getJSONObject("williamhill").getJSONObject("odds").getString("h2h");
            Log.i(TAG, odds);

            JSONArray array = jsonObject.getJSONObject("data").getJSONObject("events")
                    .getJSONObject(matchUp).getJSONObject("sites")
                    .getJSONObject("williamhill").getJSONObject("odds").getJSONArray("h2h");
            this.awayOdds = array.get(0).toString();
            this.homeOdds = array.get(1).toString();

            Log.i(TAG, "Away " + awayOdds);
            Log.i(TAG, "Home " + homeOdds);

            toAmerican();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void toAmerican(){
        double awayOddsDouble = Double.parseDouble(awayOdds);
        double homeOddsDouble = Double.parseDouble(homeOdds);

        if (awayOddsDouble >= 2.00){
            amAwayOdds = Math.round((awayOddsDouble - 1) * 100);
        }

        if (homeOddsDouble < 2.00){
            amHomeOdds = Math.round((-100)/(homeOddsDouble - 1));
        }
    }

    public String getAwayOdds() {
        return awayOdds;
    }

    public String getHomeOdds() {
        return homeOdds;
    }

    public double getAmAwayOdds() {
        return amAwayOdds;
    }

    public double getAmHomeOdds() {
        return amHomeOdds;
    }
}
