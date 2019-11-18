package com.heebin.smartroute.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.heebin.smartroute.R;
import com.heebin.smartroute.data.bean.refined.RefinedRoute;
import com.heebin.smartroute.data.inMemory.userData.RefinedRouteData;
import com.heebin.smartroute.util.constants.NomalConstants;
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
            case NomalConstants.homeToOffice:

                list = RefinedRouteData.getInstance().getRefinedh2oRoutes();
                break;
            case NomalConstants.officeToHome:

                list = RefinedRouteData.getInstance().getRefinedo2hRoutes();
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
        Log.d("RouteSelectActivity", "onItemClick: " + list.get(position).toString());
        startActivity(i);
        finish();


    }
}
