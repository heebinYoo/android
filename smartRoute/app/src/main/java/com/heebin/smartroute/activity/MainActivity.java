package com.heebin.smartroute.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.heebin.smartroute.R;
import com.heebin.smartroute.activity.init.InitActivity;
import com.heebin.smartroute.util.Constants;
import com.heebin.smartroute.view.ElementView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button office = findViewById(R.id.button); //office
        Button home = findViewById(R.id.button2); //home
        Button init = findViewById(R.id.button3); //init




        init.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, InitActivity.class);
                startActivity(intent);
            }
        });

    }
}
