package com.heebin.smartroute.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.heebin.smartroute.R;
import com.heebin.smartroute.bean.refined.RefinedRoute;
import com.heebin.smartroute.bean.userData.UserLocation;
import com.heebin.smartroute.util.Constants;
import com.heebin.smartroute.util.adapter.RefinedRouteAdapter;

import java.util.ArrayList;

public class RouteSelectActivity extends AppCompatActivity implements RefinedRouteAdapter.OnItemClickListener {
    private ArrayList<RefinedRoute> list = null;
    private int mode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_select);
        Intent intent = this.getIntent();

        mode = intent.getIntExtra("ish2o", -1);

        switch (mode){
            case Constants.homeToOffice:

                list = UserLocation.getInstance().getRefinedh2oRoutes();
                break;
            case Constants.officeToHome:

                list = UserLocation.getInstance().getRefinedo2hRoutes();
                break;
            case -1:
                Log.e("RouteSelectActivity", "onCreate: Intent is not arrived");
        }

        RecyclerView recyclerView = findViewById(R.id.recycler) ;
        recyclerView.setLayoutManager(new LinearLayoutManager(this)) ;
        RefinedRouteAdapter adapter = new RefinedRouteAdapter(list, this) ;
        recyclerView.setAdapter(adapter) ;


    }

    @Override
    public void onItemClick(View view, int position) {
        Intent i = new Intent(this, NavigateActivity.class);
        i.putExtra("selected_route", position);
        i.putExtra("ish20", mode);
        list.get(position);

    }
}
