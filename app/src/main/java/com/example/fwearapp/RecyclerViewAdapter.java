package com.example.fwearapp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private static final String TAG = "RecyclerViewAdapter";

    private ArrayList<String> mElementNames = new ArrayList<>();
    private ArrayList<Float> mElementLong = new ArrayList<>();
    private ArrayList<Float> mElementLat = new ArrayList<>();


    private Context mContext;


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView elementName;
        TextView elementLong;
        TextView elementLat;
        RelativeLayout parentLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            elementName = itemView.findViewById(R.id.element_name);
            //elementLong =  itemView.findViewById(R.id.element_long);
            //elementLat = itemView.findViewById(R.id.element_lat);
            parentLayout = itemView.findViewById(R.id.parent_layout);
        }
    }

    public RecyclerViewAdapter(ArrayList<String> elementNames,ArrayList<Float> elementLong,ArrayList<Float> elementLat, Context context) {
        mElementNames = elementNames;
        mElementLong = elementLong;
        mElementLat = elementLat;
        mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_listitem, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder, called");

        holder.elementName.setText(mElementNames.get(position));
        //holder.elementLong.setText("Longitude : " + mElementLong.get(position).toString());
        //holder.elementLat.setText("Latitude : " + mElementLat.get(position).toString());


        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick, cliched on : " + mElementNames.get(position));
                String geoString = "LONGITUDE: "  + mElementLong.get(position) + "LATITUDE: " + mElementLat.get(position);
                Toast.makeText(mContext, geoString, Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return mElementNames.size();
    }

}
