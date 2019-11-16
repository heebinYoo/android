package com.heebin.smartroute.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.heebin.smartroute.R;
import com.heebin.smartroute.util.adapter.BusAdapter;
import com.heebin.smartroute.util.adapter.StationAdapter;


public class StatusFragment extends Fragment implements BusAdapter.OnItemClickListener, StationAdapter.OnItemClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;



    public StatusFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static StatusFragment newInstance(String param1, String param2) {
        StatusFragment fragment = new StatusFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        RecyclerView busRecyclerView = getView().findViewById(R.id.bus_list);
        busRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity())) ;
        BusAdapter busAdapter = new BusAdapter(list, this) ;
        busRecyclerView.setAdapter(busAdapter) ;

        RecyclerView stationRecyclerView = getView().findViewById(R.id.station_list);
        stationRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity())) ;
        BusAdapter StationAdapter = new BusAdapter(list, this) ;
        stationRecyclerView.setAdapter(StationAdapter) ;


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_status, container, false);
    }


    //리스너 외부에서 받은거 콜하기인데, 아래꺼랑 같이
    private OnFragmentInteractionListener mListener;

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }
    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


////////////////////////////////////////////////////////////////////////////////////////


    @Override
    public void onBusItemClick(View v, int position) {

    }

    @Override
    public void onStationItemClick(View v, int position) {

    }



}
