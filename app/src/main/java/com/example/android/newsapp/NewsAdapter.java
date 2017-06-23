package com.example.android.newsapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.v7.widget.RecyclerView;
import java.util.ArrayList;
import com.squareup.picasso.Picasso;

import com.example.android.newsapp.RepoItems.NewsRepositoryItems;

/**
 * Created by Syntax Mike on 6/22/2017.
 */

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ArticleHolder> {

    private ArrayList<NewsRepositoryItems> articles;
    ItemClickListener listener;
    private Context context;

    public NewsAdapter(ArrayList<NewsRepositoryItems> articles, ItemClickListener listener){
        this.articles = articles;
        this.listener = listener;
    }

    public interface ItemClickListener {
        void onItemClick(int clickedItemIndex);
    }

    @Override
    public ArticleHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(R.layout.newsitem, parent, shouldAttachToParentImmediately);
        ArticleHolder holder = new ArticleHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(ArticleHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    class ArticleHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView title;
        TextView description;
        TextView url;
        TextView time;
        ImageView imageView;

        ArticleHolder(View view){
            super(view);
            title = (TextView)view.findViewById(R.id.title);
            description = (TextView)view.findViewById(R.id.description);
            time = (TextView) view.findViewById(R.id.time);
            imageView = (ImageView) view.findViewById(R.id.image);
            url = (TextView)view.findViewById(R.id.url);
            view.setOnClickListener(this);
        }

        public void bind(int pos){
            NewsRepositoryItems article = articles.get(pos);
            title.setText(article.getTitle());
            description.setText(article.getDescription());
            time.setText(article.getTime());
            /*
             * Picasso tutorial online
             * https://square.github.io/picasso/
             */
            Picasso.with(context).load(article.getImageURL()).resize(750, 550).into(imageView);
            url.setText(article.getUrl());
        }

        @Override
        public void onClick(View v) {
            int pos = getAdapterPosition();
            listener.onItemClick(pos);
        }
    }




}
