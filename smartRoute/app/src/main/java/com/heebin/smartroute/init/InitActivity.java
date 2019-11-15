package com.heebin.smartroute.init;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.heebin.smartroute.Constants;
import com.heebin.smartroute.R;
import com.heebin.smartroute.bean.refined.RefinedRoute;
import com.heebin.smartroute.bean.userData.UserLocation;
import com.heebin.smartroute.util.AsyncTaskCallback;
import com.heebin.smartroute.busAPI.async.HTTPAsyncRunner;
import com.heebin.smartroute.busAPI.connector.Connector;
import com.heebin.smartroute.busAPI.connector.RouteSearcherConnector;
import com.heebin.smartroute.busAPI.connector.StationSearcherConnector;

import java.util.ArrayList;

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
                startActivityForResult(i,Constants.need_home_loaction_init_google_map);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Constants.init_google_map_ok) {
            if(requestCode == Constants.need_home_loaction_init_google_map) {
                double latitude = data.getDoubleExtra("latitude", 0);
                double longitude = data.getDoubleExtra("longitude", 0);
                UserLocation.getInstance().setHome(latitude, longitude);
                Log.d("InitActivityHome", "onActivityResult: " +latitude +" "+longitude);

                inform.setText("Then, let's set your office");
                go.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent i = new Intent(InitActivity.this, InitMapsActivity.class);
                        startActivityForResult(i,Constants.need_office_loaction_init_google_map);
                    }
                });

            }
            else if(requestCode == Constants.need_office_loaction_init_google_map){
                double latitude = data.getDoubleExtra("latitude", 0);
                double longitude = data.getDoubleExtra("longitude", 0);
                UserLocation.getInstance().setOffice(latitude,longitude);
                Log.d("InitActivityOffice", "onActivityResult: " +latitude +" "+longitude);

                dialog = new ProgressDialog(this);
                dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                dialog.setMessage("데이터 확인중");
                dialog.show();

                Connector[] connector = {new StationSearcherConnector(), new RouteSearcherConnector()};
                new HTTPAsyncRunner(this, connector).execute(1);


            }

        }
    }

    @Override
    public void onSuccess(String result) {
        dialog.dismiss();

        UserLocation.getInstance().getRefinedh2oRoutes().forEach(refinedRoute -> Log.d("Refine", "home to office: "+refinedRoute.toString()));

        //ArrayList<RefinedRoute> debug = UserLocation.getInstance().getRefinedo2hRoutes();

        UserLocation.getInstance().getRefinedo2hRoutes().forEach(refinedRoute -> Log.d("Refine", "office to home: "+refinedRoute.toString()));

        UserLocation x = UserLocation.getInstance();

        finish();


    }

    @Override
    public void onFailure(Exception e) {

    }
}
