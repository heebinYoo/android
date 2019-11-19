package com.heebin.smartroute.util.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.heebin.smartroute.R;
import com.heebin.smartroute.data.bean.raw.Bus;

import java.util.ArrayList;

public class BusAdapter extends RecyclerView.Adapter<BusAdapter.ViewHolder> {
    OnItemClickListener onItemClickListener;
    ArrayList<Bus> mData;

    public BusAdapter(ArrayList<Bus> mData, OnItemClickListener onItemClickListener){
        this.mData=mData;
        this.onItemClickListener=onItemClickListener;
    }

    @NonNull
    @Override
    public BusAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext() ;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;

        View view = inflater.inflate(R.layout.recyclerview_item_bus, parent, false) ;
        BusAdapter.ViewHolder vh = new BusAdapter.ViewHolder(view) ;

        return vh ;
    }

    @Override
    public void onBindViewHolder(@NonNull BusAdapter.ViewHolder holder, int position) {
        Bus bus = mData.get(position) ;
        holder.name.setText(bus.getBusName()) ;
        holder.score.setText("");

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public interface OnItemClickListener {
        void onBusItemClick(View v, int position) ;
    }
    class ViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        TextView score;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.bus_name);
            score=itemView.findViewById(R.id.bus_score);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition() ;
                    if (pos != RecyclerView.NO_POSITION) {
                        onItemClickListener.onBusItemClick(v,pos);
                    }
                }
            });
        }
    }

}
