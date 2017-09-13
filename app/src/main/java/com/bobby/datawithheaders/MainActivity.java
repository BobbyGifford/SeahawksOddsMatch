package com.bobby.datawithheaders;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ArrayList<String> games = new ArrayList<>();
    private String matchUp;
    private RawData data;
    private TextView away;
    private TextView home;
    private TextView opponent;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Handler handler = new Handler();
        data = new RawData();
        away = (TextView) findViewById(R.id.awayOdds);
        home = (TextView) findViewById(R.id.homeOdds);
        opponent = (TextView) findViewById(R.id.opponent);
        opponent.setText("San Francisco 49ers\n@\nSeattle");

        matchUp = "San Francisco 49ers_Seattle Seahawks";
//                data.execute("API Key Here",
//                matchUp);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                away.setText(data.getAwayOdds()+"\n"+
                "(American)" + data.getAmAwayOdds());
                home.setText(data.getHomeOdds()+"\n"+
                "(American) " + data.getAmHomeOdds());
                Toast.makeText(getApplicationContext(),
                        "Works", Toast.LENGTH_SHORT).show();
            }
        }, 20000);

    }
}
