package com.harshbalot.codefugitives;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng jaipur = new LatLng(26.91, 75.78);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(jaipur, 12));

        mMap.addMarker(new MarkerOptions().position(jaipur).title("Marker outside Perimeter"));

        new bgTask().execute();

//        mMap.addCircle(new CircleOptions()
//                .center(new LatLng(-33.87365, 151.20689))
//                .radius(10000)
//                .strokeColor(Color.RED)
//                .fillColor(Color.BLUE));

    }

    private class bgTask extends AsyncTask<Void, Void, Void> {

        LinkedList<Double> lat = new LinkedList<>();
        LinkedList<Double> lng = new LinkedList<>();
        LinkedList<Double> id = new LinkedList<>();
        LinkedList<Double> fid = new LinkedList<>();
        LinkedList<Double> param = new LinkedList<>();
        LinkedList<Double> s_id = new LinkedList<>();
        LinkedList<Double> s_lat = new LinkedList<>();
        LinkedList<Double> s_lng = new LinkedList<>();

        @Override
        protected Void doInBackground(Void... voids) {
            Connection dbConnection = null;
            PreparedStatement preparedStatement = null;
            String selectTableSQL = "SELECT s_id, f_id, params, lats, longd FROM bands";
            String selectTableSQL2 = "SELECT station_id,latitude,longitude FROM station";


            try {
                Class.forName("com.mysql.jdbc.Driver");

                String dbName = "hackme";
                String userName = "xyz";
                String hostname = "192.168.43.30";
                String port = "3306";
                String jdbcUrl = "jdbc:mysql://" + hostname + ":" + port + "/" + dbName;// + "?user=" + userName + "&password=" + password;
                System.out.println(jdbcUrl);
                dbConnection = DriverManager.getConnection(jdbcUrl, userName, "");
                preparedStatement = dbConnection.prepareStatement(selectTableSQL);
                ResultSet rs = preparedStatement.executeQuery(selectTableSQL);
                while (rs.next()) {
                    id.add(rs.getDouble("s_id"));
                    fid.add(rs.getDouble("f_id"));
                    param.add(rs.getDouble("params"));
                    lat.add(rs.getDouble("lats"));
                    lng.add(rs.getDouble("longd"));
                }
                preparedStatement = dbConnection.prepareStatement(selectTableSQL2);
                rs = preparedStatement.executeQuery(selectTableSQL2);
                while (rs.next()) {
                    s_id.add(rs.getDouble("station_id"));
                    s_lat.add(rs.getDouble("latitude"));
                    s_lng.add(rs.getDouble("longitude"));
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            System.out.println(lat);
            for (int i = 0; i < lat.size(); i++) {
                for (int j = 0; j < s_id.size(); j++) {
                    if (id.get(i).equals(s_id.get(j))) {
                        if ((lat.get(i) < (s_lat.get(j) + (param.get(i) * 0.01)) && lat.get(i) > (s_lat.get(j) - (param.get(i) * 0.01))) && (lng.get(i) < (s_lng.get(j) + (param.get(i) * 0.01))) && (lng.get(i) > (s_lng.get(j) - (param.get(i) * 0.01)))) {
                            System.out.println("Inside Perimeter");
                            Double latt = lat.get(i);
                            Double lngg = lng.get(i);
                            mMap.addMarker(new MarkerOptions().position(new LatLng(latt, lngg)).title("Marker in Perimeter").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                        } else {
                            System.out.println("Outside Perimeter");
                            Double latt = lat.get(i);
                            Double lngg = lng.get(i);
                            mMap.addMarker(new MarkerOptions().position(new LatLng(latt, lngg)).title("Marker outside Perimeter").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
                        }
                    }
                }
            }
        }
    }
}
