package com.heebin.smartroute.init;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.heebin.smartroute.Constants;
import com.heebin.smartroute.InitMapsActivity;
import com.heebin.smartroute.R;

public class InitActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_init);
        Button go = findViewById(R.id.gobtn1);
        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(InitActivity.this, InitMapsActivity.class);
                startActivityForResult(i,100);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Constants.init_google_map_ok){
            double latitude = data.getDoubleExtra("latitude",0);
            double longitude = data.getDoubleExtra("longitude",0);
        }
    }
}
