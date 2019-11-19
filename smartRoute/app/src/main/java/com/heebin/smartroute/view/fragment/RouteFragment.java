package com.heebin.smartroute.view.fragment;

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
import com.heebin.smartroute.data.bean.refined.BusStationMatrix;
import com.heebin.smartroute.learning.Matrix.BusStationMatrixHolder;
import com.heebin.smartroute.util.adapter.DetailedStationAdapter;


public class RouteFragment extends Fragment {

    private  Context context;

    private OnRouteFragmentListener mListener;
private DetailedStationAdapter detailedStationAdapter;
    public RouteFragment() {

    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_route, container, false);
        setRecycler(rootView);


        return rootView;
    }
    private void setRecycler(ViewGroup rootView) {

        RecyclerView recyclerView = rootView.findViewById(R.id.detailedStationView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        detailedStationAdapter = new DetailedStationAdapter(BusStationMatrixHolder.getInstance().getDetailStations(), BusStationMatrixHolder.getInstance().getTargetRoute().getMainPathes());
        recyclerView.setAdapter(detailedStationAdapter);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context=context;
        if (context instanceof OnRouteFragmentListener) {
            mListener = (OnRouteFragmentListener) context;
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


    public interface OnRouteFragmentListener {

        void onRouteInteraction();
    }
}
