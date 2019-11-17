package com.heebin.smartroute.view.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.heebin.smartroute.R;
import com.heebin.smartroute.data.database.async.DBLoadAsyncRunner;
import com.heebin.smartroute.util.async.AsyncTaskCallback;
import com.heebin.smartroute.util.constants.NomalConstants;
import com.heebin.smartroute.view.activity.init.InitActivity;

public class MainActivity extends AppCompatActivity implements AsyncTaskCallback {
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button office = findViewById(R.id.button); //office
        Button home = findViewById(R.id.button2); //home
        Button init = findViewById(R.id.button3); //init


        SharedPreferences sharedPreferences = getSharedPreferences("init", Context.MODE_PRIVATE);
       if(sharedPreferences.getBoolean("inited",false)){
           init.setText("already done");
           init.setEnabled(false);
           new DBLoadAsyncRunner(this,this).execute(1);
           dialog = new ProgressDialog(this);
           dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
           dialog.setMessage("데이터 로드중");
           dialog.show();
       }
        else {
           init.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   Intent intent = new Intent(MainActivity.this, InitActivity.class);
                   startActivity(intent);
               }
           });
       }

    }

    @Override
    public void onSuccess(String result) {
        if(NomalConstants.DB_LOAD_good==result){
            dialog.dismiss();
        }
    }

    @Override
    public void onFailure(Exception e) {

    }
}
