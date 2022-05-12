package com.example.newsapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

//use  to send the data to each item of recyleview

public class NewsRVAdapter extends RecyclerView.Adapter<NewsRVAdapter.ViewHolder> {
    public NewsRVAdapter(ArrayList<Articles> articlesArrayList, Context context) {
        this.articlesArrayList = articlesArrayList;
        this.context = context;
    }

    private ArrayList<Articles> articlesArrayList;
    private Context context;

    @NonNull
    @Override

    public NewsRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_rv_item,parent,false);
        //inflate the layout news_rv_item xml file
        return  new NewsRVAdapter.ViewHolder(view);
    }
    // the data to the textview
    @Override
    public void onBindViewHolder(@NonNull NewsRVAdapter.ViewHolder holder, int position) {
        Articles articles = articlesArrayList.get(position);
        holder.subTitleTV.setText(articles.getDescription());
        holder.titleTV.setText(articles.getTitle());
        Picasso.get().load(articles.getUrlToImage()).into(holder.newsIV);
        // for image or load the url
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context,NewsDeatilActivity.class);
                i.putExtra("title",articles.getTitle());
                i.putExtra("content",articles.getContent());
                i.putExtra("desc",articles.getDescription());
                i.putExtra("image",articles.getUrlToImage());
                i.putExtra("url",articles.getUrl());
                context.startActivity(i);

            }
        });

        }


    @Override
    public int getItemCount() {
        return articlesArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        //image view and two text view
        private TextView titleTV, subTitleTV;
        private ImageView newsIV;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTV = itemView.findViewById(R.id.idIVNewsHeading);
            subTitleTV = itemView.findViewById(R.id.idTVSubTitle);
            newsIV = itemView.findViewById(R.id.idIVNews);

        }
    }
}
