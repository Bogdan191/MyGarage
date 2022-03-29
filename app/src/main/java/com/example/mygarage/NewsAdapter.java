package com.example.mygarage;

import android.content.Context;
import android.net.Uri;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.MyViewHolder> {

    Context context;

    ArrayList<News> listNews;

    public NewsAdapter(Context context, ArrayList<News> listNews) {
        this.context = context;
        this.listNews = listNews;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.news_item, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        News news = listNews.get(position);

        holder.title.setText(news.getTitle());
        holder.description.setText(news.getDescription());

        Picasso.get().load(news.getImageLink()).into(holder.imageNews);
    }

    @Override
    public int getItemCount() {
        return listNews.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView title, description;
        ImageView imageNews;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.tvTitleNews);
            description = itemView.findViewById(R.id.tvDescriptionNews);
            imageNews = itemView.findViewById(R.id.imageNews);
        }
    }
}
