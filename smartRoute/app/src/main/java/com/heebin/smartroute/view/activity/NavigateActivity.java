package com.heebin.smartroute.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.heebin.smartroute.R;
import com.heebin.smartroute.data.bean.refined.RefinedRoute;
import com.heebin.smartroute.data.database.async.DBUpdateAsyncRunner;
import com.heebin.smartroute.learning.Matrix.BusStationAsyncBuilder;
import com.heebin.smartroute.learning.Matrix.BusStationMatrixHolder;
import com.heebin.smartroute.data.inMemory.userData.RefinedRouteData;
import com.heebin.smartroute.util.async.AsyncTaskCallback;
import com.heebin.smartroute.view.fragment.RouteFragment;
import com.heebin.smartroute.view.fragment.StatusFragment;
import com.heebin.smartroute.util.constants.NomalConstants;

public class NavigateActivity extends AppCompatActivity implements View.OnClickListener, RouteFragment.OnRouteFragmentListener, StatusFragment.OnStatusFragmentListener, AsyncTaskCallback {
    private int mode;
    private RefinedRoute route;

    private RouteFragment routeFragment;
    private StatusFragment statusFragment;

    private Button routeBtn;
    private Button statusBtn;

    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        setContentView(R.layout.activity_navigate);
        Intent i = getIntent();
        int selected_route = i.getIntExtra("selected_route",-1);
        mode= i.getIntExtra("ish20",-1);

        switch (mode){
            case NomalConstants.homeToOffice:
                this.route = RefinedRouteData.getInstance().getRefinedh2oRoutes().get(selected_route);
                break;
            case NomalConstants.officeToHome:
                this.route = RefinedRouteData.getInstance().getRefinedo2hRoutes().get(selected_route);
                break;
            case -1:
                Log.e("NavigateActivity", "onCreate: Intent is not arrived");
        }
        new BusStationAsyncBuilder(this.route, this, this).execute(1);

        dialog = new ProgressDialog(this);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage("데이터 로드중");
        dialog.show();



    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dialog.dismiss();
    }

    @Override
    public void onClick(View view) {
        if(view == routeBtn){
            if(!routeFragment.isVisible()){
                FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
                ft.addToBackStack(null);
                ft.replace(R.id.main_fragment, routeFragment);
                ft.commit();
            }
        }
        else if(view==statusBtn){
            if(!statusFragment.isVisible()){
                FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
                ft.addToBackStack(null);
                ft.replace(R.id.main_fragment, statusFragment);
                ft.commit();
            }
        }
    }

    @Override
    public void onRouteInteraction() {

    }

    @Override
    public void onStatusInteraction() {

    }

    @Override
    public void onSuccess(String result) {
        if(result.equals("good")) {
            dialog.dismiss();

            new DBUpdateAsyncRunner(this).execute(1);

            routeFragment = new RouteFragment();
            statusFragment = new StatusFragment();

            routeBtn = findViewById(R.id.route);
            statusBtn = findViewById(R.id.status);

            routeBtn.setOnClickListener(this);
            statusBtn.setOnClickListener(this);

            FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
            ft.addToBackStack(null);
            ft.add(R.id.main_fragment, statusFragment);
            ft.commitAllowingStateLoss();
        }
    }

    @Override
    public void onFailure(Exception e) {

    }
}
