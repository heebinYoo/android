package com.heebin.smartroute.view.fragment;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.heebin.smartroute.R;
import com.heebin.smartroute.data.bean.raw.Bus;
import com.heebin.smartroute.data.bean.raw.Station;
import com.heebin.smartroute.learning.Matrix.BusStationMatrixHolder;
import com.heebin.smartroute.util.Distance;
import com.heebin.smartroute.util.adapter.BusAdapter;
import com.heebin.smartroute.util.adapter.StationAdapter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Timer;
import java.util.TimerTask;

import static android.location.LocationManager.GPS_PROVIDER;


public class StatusFragment extends Fragment implements BusAdapter.OnItemClickListener, StationAdapter.OnItemClickListener,LocationListener {

    private OnStatusFragmentListener mListener;
    private Context context;
    private StationAdapter stationAdapter;
    private BusAdapter busAdapter;
    private Location location;

    private ArrayList<Station> stations=new ArrayList<Station>();
    private ArrayList<Bus> buses = new ArrayList<Bus>();


        @Override
        public void onLocationChanged(Location location) {
            this.location=location;
            Log.d("where", "initMap: " + location.getLatitude() + " :: " +location.getLongitude());
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {

        }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_status, container, false);

        setLocation();
        setRecycler(rootView);
        return rootView;
    }
    private void setRecycler(ViewGroup rootView){

        setList();

        RecyclerView busRecyclerView = rootView.findViewById(R.id.bus_list);
        busRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        busAdapter = new BusAdapter(buses, this);
        busRecyclerView.setAdapter(busAdapter);

        RecyclerView stationRecyclerView = rootView.findViewById(R.id.station_list);
        stationRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        stationAdapter = new StationAdapter(stations, this);
        stationRecyclerView.setAdapter(stationAdapter);
    }

    private void setLocation() {
        if (context.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && context.checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(context,"location permission problem",Toast.LENGTH_LONG);
        }

        final LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
       location =  lm.getLastKnownLocation(GPS_PROVIDER);
        lm.requestLocationUpdates(GPS_PROVIDER, 3000,10,this);

    }

    private void setList(){
        buses.clear();
        stations.clear();

        //HashSet<Bus> busHashSet = new HashSet<Bus>();


        for (int i = 0; i < BusStationMatrixHolder.getInstance().getDetailStations().size(); i++) {
            Station station = BusStationMatrixHolder.getInstance().getDetailStations().get(i);
            if(Distance.distance(location.getLatitude(),location.getLongitude(),station.getGpsY(),station.getGpsX())<1000){
                stations.add(station);
                //busHashSet.addAll(BusStationMatrixHolder.getInstance().getAvailableBus(i));
            }
        }
        //buses.addAll(busHashSet);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
        if (context instanceof OnStatusFragmentListener) {
            mListener = (OnStatusFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }


        Timer timer = new Timer(true); //인자가 Daemon 설정인데 true 여야 죽지 않음.
        Handler handler = new Handler();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable(){
                    public void run(){

                        setList();
                        busAdapter.notifyDataSetChanged();
                        stationAdapter.notifyDataSetChanged();
                    }
                });
            }
        }, 0, 5000); //시작지연시간 0, 주기 10초




    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnStatusFragmentListener {

        void onStatusInteraction();
    }



    @Override
    public void onBusItemClick(View v, int position) {
        Log.d("StatusFragment", "onBusItemClick: "+BusStationMatrixHolder.getInstance().getRelatedBus().get(position).getBusName());


    }

    @Override
    public void onStationItemClick(View v, int position) {
        Log.d("StatusFragment", "onStationItemClick: "+ BusStationMatrixHolder.getInstance().getDetailStations().get(position).getStationName());

    }



}
