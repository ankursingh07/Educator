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

public class PostFeedAdapter extends RecyclerView.Adapter<PostFeedAdapter.ViewHolder> {

    Context context;
    ArrayList<ProjectModel> projectModelArrayList;


    public PostFeedAdapter(Context context,ArrayList<ProjectModel> projectModelArrayList) {
        this.context = context;
        this.projectModelArrayList=projectModelArrayList;
    }

    @NonNull
    @Override
    public PostFeedAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.projectfeedlayout,parent,false);
        return new PostFeedAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(context).load(projectModelArrayList.get(position).getImageUrl()).into(holder.imageView);
        String sum="",head="";
        sum=projectModelArrayList.get(position).getbDes().toString();
        head=projectModelArrayList.get(position).getHeading().toString();
        holder.summary.setText(sum);
        holder.heading.setText(head);
        holder.host.setText(projectModelArrayList.get(position).getHost().toString());
        holder.description.setText(projectModelArrayList.get(position).getfDes().toString());
        holder.github.setText(projectModelArrayList.get(position).getGit().toString());
    }

    @Override
    public int getItemCount() {
        return projectModelArrayList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView heading,summary,description,github,host;
        ImageView imageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            heading=itemView.findViewById(R.id.headingProjectFeed);
            summary=itemView.findViewById(R.id.summaryProjectFeed);
            imageView=itemView.findViewById(R.id.ProjectfeedImageView);
            description=itemView.findViewById(R.id.DescriptionProjectFeed);
            github=itemView.findViewById(R.id.githubUrlProjectFeed);
            host=itemView.findViewById(R.id.hostUrlProjectFeed);
        }
    }
}

