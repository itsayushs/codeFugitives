package com.harshbalot.tracker;

import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button btnShowLocation;
    GPSTracker gps;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnShowLocation =  findViewById(R.id.btnShowLocation);

        BatteryStatus receiver = new BatteryStatus();
        IntentFilter inf = new IntentFilter();
        inf.addAction("android.intent.action.BATTERY_CHANGED");
        registerReceiver(receiver, inf);

        // create class object
        gps = new GPSTracker(MainActivity.this);


        // show location button click event
        btnShowLocation.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                // check if GPS enabled
                if(gps.canGetLocation()){

                    double latitude = gps.getLatitude();
                    double longitude = gps.getLongitude();

                    // \n is for new line
                    doSomethingUseful();
                    Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
                }else{
                    // can't get location
                    // GPS or Network is not enabled
                    // Ask user to enable GPS/network in settings
                    gps.showSettingsAlert();
                }

            }
        });
    }
    private void doSomethingUseful(){

        Thread t1 = new Thread() {
            @Override
            public void run(){
                try{
                    while(true){
                        sleep(1000 * 60 * 1);
                         backgroundUpdater();
                    }
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        };

        t1.start();
    }


    private void backgroundUpdater() {
        ArrayList<Double> locations = null;
        if(gps.canGetLocation()){

                double latitude = gps.getLatitude();
                double longitude = gps.getLongitude();

                locations = new ArrayList<>();

                locations.add(latitude);
                locations.add(longitude);
                System.out.println(locations);
        }else{
                // can't get location
                // GPS or Network is not enabled
                // Ask user to enable GPS/network in settings
                gps.showSettingsAlert();
                return;
        }
            Connection dbConnection = null;
            PreparedStatement preparedStatement = null;
            String updateTableSQL = "UPDATE bands SET lats = ?, longd = ? WHERE band_id = ?";

            try {
                Class.forName("com.mysql.jdbc.Driver");

                String dbName = "hackme";
                String userName = "xyz";
                String password = "";
                String hostname = "192.168.43.30";
                String port = "3306";
                String jdbcUrl = "jdbc:mysql://" + hostname + ":" + port + "/" + dbName;// + "?user=" + userName + "&password=" + password;
                System.out.println(jdbcUrl);
                dbConnection = DriverManager.getConnection(jdbcUrl, userName, "");
                preparedStatement = dbConnection.prepareStatement(updateTableSQL);

                preparedStatement.setString(1, locations.get(0)+"");
                preparedStatement.setString(2, locations.get(1)+"");
                preparedStatement.setInt(3, 12);

                System.out.println(preparedStatement.toString());
                preparedStatement.executeUpdate();


            }catch (Exception e){
                e.printStackTrace();
            }
        }

}
