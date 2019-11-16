package com.heebin.smartroute.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.heebin.smartroute.R;
import com.heebin.smartroute.bean.refined.RefinedRoute;
import com.heebin.smartroute.bean.userData.UserLocation;
import com.heebin.smartroute.fragment.RouteFragment;
import com.heebin.smartroute.fragment.StatusFragment;
import com.heebin.smartroute.util.Constants;

public class NavigateActivity extends AppCompatActivity implements View.OnClickListener {
    private int mode;
    private RefinedRoute route;

    private RouteFragment routeFragment;
    private StatusFragment statusFragment;

    private Button routeBtn;
    private Button statusBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        setContentView(R.layout.activity_navigate);
        Intent i = getIntent();
        int selected_route = i.getIntExtra("selected_route",-1);

        mode= i.getIntExtra("ish20",-1);

        switch (mode){
            case Constants.homeToOffice:
                this.route =   UserLocation.getInstance().getRefinedh2oRoutes().get(selected_route);
                break;
            case Constants.officeToHome:
                this.route = UserLocation.getInstance().getRefinedo2hRoutes().get(selected_route);
                break;
            case -1:
                Log.e("NavigateActivity", "onCreate: Intent is not arrived");
        }

        routeFragment = new RouteFragment();
        statusFragment = new StatusFragment();

        routeBtn = findViewById(R.id.route);
        statusBtn = findViewById(R.id.status);

        routeBtn.setOnClickListener(this);
        statusBtn.setOnClickListener(this);

        FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
        ft.addToBackStack(null);
        ft.add(R.id.main_fragment, routeFragment);
        ft.commit();

    }

    @Override
    public void onClick(View view) {
        if(view == routeBtn){
            if(!routeFragment.isVisible()){
                FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
                ft.addToBackStack(null);
                ft.add(R.id.main_fragment, routeFragment);
                ft.commit();
            }
        }
        else if(view==statusBtn){
            if(!statusFragment.isVisible()){
                FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
                ft.addToBackStack(null);
                ft.add(R.id.main_fragment, statusFragment);
                ft.commit();
            }
        }
    }
}
