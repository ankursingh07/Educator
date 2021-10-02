package com.example.login;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class HomeFeedAdapter extends RecyclerView.Adapter<HomeFeedAdapter.ViewHolder> {

    Context context;
    ArrayList<PostModel> modelArrayList;



    public HomeFeedAdapter(Context context,ArrayList<PostModel> modelArrayList) {
        this.context = context;
        this.modelArrayList=modelArrayList;

    }

    @NonNull
    @Override
    public HomeFeedAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.homefeed,parent,false);
        return new HomeFeedAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeFeedAdapter.ViewHolder holder, int position) {


        //holder.heading.setText("This is Sample Heading");
        //holder.summary.setText("This is Sample Summary");

            Glide.with(context).load(modelArrayList.get(position).getImageurl()).into(holder.imageView);
            holder.summary.setText(modelArrayList.get(position).getText().toString());


    }

    @Override
    public int getItemCount() {
        return modelArrayList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView heading,summary;
        ImageView imageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            heading=itemView.findViewById(R.id.headingHomeFeed);
            summary=itemView.findViewById(R.id.summaryHomeFeed);
            imageView=itemView.findViewById(R.id.feedImageView);
        }
    }
}
