package com.heebin.smartroute.view.activity.init;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.heebin.smartroute.data.database.async.DBInitAsyncRunner;
import com.heebin.smartroute.util.constants.NomalConstants;
import com.heebin.smartroute.R;
import com.heebin.smartroute.data.inMemory.userData.LodgmentData;
import com.heebin.smartroute.util.async.AsyncTaskCallback;
import com.heebin.smartroute.busAPI.async.HTTPAsyncRunner;
import com.heebin.smartroute.busAPI.connector.Connector;
import com.heebin.smartroute.busAPI.connector.async.RouteSearcherConnector;
import com.heebin.smartroute.busAPI.connector.async.NearStationSearcherConnector;

public class InitActivity extends AppCompatActivity implements AsyncTaskCallback {
    Button go;
    TextView inform;
    ProgressDialog dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_init);
        go = findViewById(R.id.gobtn);
        inform = findViewById(R.id.init_text_view);
        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(InitActivity.this, InitMapsActivity.class);
                startActivityForResult(i, NomalConstants.need_home_loaction_init_google_map);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == NomalConstants.init_google_map_ok) {
            if(requestCode == NomalConstants.need_home_loaction_init_google_map) {
                double latitude = data.getDoubleExtra("latitude", 0);
                double longitude = data.getDoubleExtra("longitude", 0);
                LodgmentData.getInstance().setHome(latitude, longitude);
                Log.d("InitActivityHome", "onActivityResult: " +latitude +" "+longitude);

                inform.setText("Then, let's set your office");
                go.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent i = new Intent(InitActivity.this, InitMapsActivity.class);
                        startActivityForResult(i, NomalConstants.need_office_loaction_init_google_map);
                    }
                });

            }
            else if(requestCode == NomalConstants.need_office_loaction_init_google_map){
                double latitude = data.getDoubleExtra("latitude", 0);
                double longitude = data.getDoubleExtra("longitude", 0);
                LodgmentData.getInstance().setOffice(latitude,longitude);
                Log.d("InitActivityOffice", "onActivityResult: " +latitude +" "+longitude);

                dialog = new ProgressDialog(this);
                dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                dialog.setMessage("데이터 확인중");
                dialog.show();

                Connector[] connector = {new NearStationSearcherConnector(), new RouteSearcherConnector()};
                new HTTPAsyncRunner(this, connector).execute(1);


            }

        }
    }

    @Override
    public void onSuccess(String result) {
        if(result.equals(NomalConstants.HTTP_good)){

            new DBInitAsyncRunner(this, this).execute(1);
        }
        else if(result.equals(NomalConstants.DB_good)) {

            SharedPreferences sharedPreferences = getSharedPreferences("init", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("inited", true);
            editor.putString("homeLat", String.format("%.6f",LodgmentData.getInstance().getHomeLat()));
            editor.putString("homeLong",String.format("%.6f",LodgmentData.getInstance().getOfficeLong()));
            editor.putString("officeLat",String.format("%.6f",LodgmentData.getInstance().getOfficeLat()));
            editor.putString("officeLong",String.format("%.6f",LodgmentData.getInstance().getOfficeLong()));
            editor.commit();

            dialog.dismiss();
            finish();
        }

    }

    @Override
    public void onFailure(Exception e) {

    }
}

//ArrayList<RefinedRoute> debug = LodgmentData.getInstance().getRefinedo2hRoutes();
//LodgmentData debug = LodgmentData.getInstance();
//RouteData.getInstance().getRefinedh2oRoutes().forEach(refinedRoute -> Log.d("Refine", "home to office: "+refinedRoute.toString()));
//RouteData.getInstance().getRefinedo2hRoutes().forEach(refinedRoute -> Log.d("Refine", "office to home: "+refinedRoute.toString()));

//BusStationMatrix bsm = new BusStationMatrix(RouteData.getInstance().getRefinedo2hRoutes().get(0));
//Log.d("BusStationMatrix", "onSuccess: "+bsm.toString());