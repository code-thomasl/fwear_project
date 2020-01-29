package com.example.fwearapp;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class SendMessageActivity extends Activity implements View.OnClickListener {

    private static final String TAG = "SendMessageActivity";
    private TextView mTextView;
    EditText mEdit;
    private Student student;
    private String messageToSend;
    public double latitudeToSend;
    public double longitudeToSend;
    int PERMISSION_ID = 12;


    // Manage location (latitude, longitude)
    FusedLocationProviderClient mFusedLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_message);

        Button sendMsgBck = findViewById(R.id.send);
        sendMsgBck.setOnClickListener((View.OnClickListener) this);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);


        //Just an example for testing purpose
        // student_id : should be generated randomly and saved
        // gps_lat : use getLocation (see MainActivity)
        // gps_long : use getLocation (see MainActivity)
        // student_message : let user fill the form and get it back
        /*
        student.setStudent_id(20132491);
        student.setGps_lat(34.001f);
        student.setGps_long(3.235f);
        student.setStudent_message("hello world");

         */


        //mTextView = findViewById(R.id.text_view_result);




        // Enables Always-on
        //setAmbientEnabled();
    }

    @Override
    public void onClick(View view) {
        postMessage();
    }

    public void postMessage() {
        //post to this https://hmin309-embedded-systems.herokuapp.com/message-exchange/messages/
        // use Retrofit library ?

        OkHttpClient okHttpClient = new OkHttpClient();

        MediaType JSON_TYPE = MediaType.parse("application/json; charset=utf-8");
        /*String myJson =
                "  {\n" +
                "    \"student_id\": 20130039,\n" +
                "    \"gps_lat\": 36.001,\n" +
                "    \"gps_long\": 3.235,\n" +
                "    \"student_message\": \"message3\"\n" +
                "  }";


        */

        /*
        String myJson = new StringBuilder()
                .append("{")
                .append("\"student_id\":\"20130039\",")
                .append("\"gps_lat\":\"34.001\",")
                .append("\"gps_long\":\"3.235\",")
                .append("\"student_message\":\"message4\",")
                .append("}").toString();
        */

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        getLastLocation();


        mEdit = (EditText)findViewById(R.id.editText);

        messageToSend = mEdit.getText().toString();
        //latitudeToSend = (float)50.0;
        //longitudeToSend = (float)3.2;


        JSONObject postdata = new JSONObject();
        try
        {
            /*
            postdata.put("student_id", student.getStudent_id());
            postdata.put("gps_lat", student.getGps_lat());
            postdata.put("gps_long", student.getGps_long());
            postdata.put("student_message", student.getStudent_message());
             */

            getLastLocation();

            postdata.put("student_id", 20130040);
            postdata.put("gps_lat", latitudeToSend);
            postdata.put("gps_long",  longitudeToSend);
            postdata.put("student_message", messageToSend);

        } catch(JSONException e)
        {
            e.printStackTrace();
        }

        //Création de la requête POST
        RequestBody body = RequestBody.create(
                MediaType.parse("application/json"),
                postdata.toString()
        );


        Request myPostRequest = new Request.Builder()
                .url("https://hmin309-embedded-systems.herokuapp.com/message-exchange/messages/")
                .post(body)
                .build();


        okHttpClient.newCall(myPostRequest).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                String mMessage = e.getMessage().toString();
                Log.w("failure Response", mMessage);
                //call.cancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //le retour est effectué dans un thread différent
                //final String text = response.body().string();

                String mMessage = response.body().string();
                Log.e(TAG, mMessage);
                Log.d(TAG, "[[Messsage has been posted]]");

                /*
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //mTextView.setText(text);
                    }
                });
                 */
            }


        });

        Log.d(TAG, "Messsage has been posted");
        //Toast.makeText(this, "Message has been posted", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private LocationCallback mLocationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            Location mLastLocation = locationResult.getLastLocation();
            //latTextView.setText(mLastLocation.getLatitude()+"");
            //lonTextView.setText(mLastLocation.getLongitude()+"");
        }
    };

    // check permission for location access
    private boolean checkPermissions(){
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            return true;
        }
        return false;
    }

    // request permission for location access
    private void requestPermissions(){
        ActivityCompat.requestPermissions(
                this,
                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                PERMISSION_ID
        );
    }

    // gives back result for location access permission response
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_ID) {
            if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                // Granted. Start getting the location information
            }
        }
    }

    // check if Location is enabled on the device
    private boolean isLocationEnabled(){
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
                LocationManager.NETWORK_PROVIDER
        );
    }


    // request the current location of the device, used in getLastLocation function
    @SuppressLint("MissingPermission")
    private void requestNewLocationData(){

        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(0);
        mLocationRequest.setFastestInterval(0);
        mLocationRequest.setNumUpdates(1);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mFusedLocationClient.requestLocationUpdates(
                mLocationRequest, mLocationCallback,
                Looper.myLooper()
        );

    }

    // getting the Last Location of the Device (if user authorized it first)
    @SuppressLint("MissingPermission")
    private void getLastLocation(){
        if (checkPermissions()) {
            if (isLocationEnabled()) {
                mFusedLocationClient.getLastLocation().addOnCompleteListener(
                        new OnCompleteListener<Location>() {
                            @Override
                            public void onComplete(@NonNull Task<Location> task) {
                                Location location = task.getResult();
                                if (location == null) {
                                    requestNewLocationData();
                                } else {
                                    //latTextView.setText(location.getLatitude()+"");
                                    //lonTextView.setText(location.getLongitude()+"");
                                    latitudeToSend = location.getLatitude();
                                    longitudeToSend = location.getLongitude();
                                }
                            }
                        }
                );
            } else {
                Toast.makeText(this, "Turn on location", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        } else {
            requestPermissions();
        }
    }

}


