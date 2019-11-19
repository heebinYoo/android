package com.heebin.smartroute.util.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Dimension;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.heebin.smartroute.R;
import com.heebin.smartroute.data.bean.raw.Station;
import com.heebin.smartroute.data.bean.refined.RefinedPath;

import java.util.ArrayList;

public class DetailedStationAdapter extends RecyclerView.Adapter<DetailedStationAdapter.ViewHolder>{

   private ArrayList<Station> stations;
    private ArrayList<RefinedPath> mainPathes;

    public DetailedStationAdapter(ArrayList<Station> detailStations, ArrayList<RefinedPath> mainPathes) {
        this.stations=detailStations;
        this.mainPathes=mainPathes;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext() ;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;

        View view = inflater.inflate(R.layout.recyclerview_item_detailedstation, parent, false) ;
        DetailedStationAdapter.ViewHolder vh = new DetailedStationAdapter.ViewHolder(view) ;

        return vh ;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Station station = stations.get(position) ;

        holder.name.setText(station.getStationName()) ;
    }

    @Override
    public int getItemCount() {
        return stations.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView name;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.textView3);

        }
    }

}
