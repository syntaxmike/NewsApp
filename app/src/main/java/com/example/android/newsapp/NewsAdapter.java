package com.example.android.newsapp;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.v7.widget.RecyclerView;
import java.util.ArrayList;

import com.example.android.newsapp.data.Contract;
import com.squareup.picasso.Picasso;

import com.example.android.newsapp.data.NewsItem;

/**
 * Created by Syntax Mike on 6/22/2017.
 */

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ArticleHolder> {

    private Cursor mCursor;
    private ItemClickListener mListener;
    private Context mContext;

    public NewsAdapter(Cursor cursor, ItemClickListener listener){
        this.mCursor = cursor;
        this.mListener = listener;
    }

    public interface ItemClickListener {
        void onItemClick(Cursor cursor, int clickedItemIndex);
    }

    @Override
    public ArticleHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(mContext);
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
        return mCursor.getCount();
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
            view.setOnClickListener(this);
        }

        public void bind(int pos){
            mCursor.moveToPosition(pos);
            title.setText(mCursor.getString(mCursor.getColumnIndex(Contract.TABLE_NEWS.COLUMN_TITLE)));
            description.setText(mCursor.getString(mCursor.getColumnIndex(Contract.TABLE_NEWS.COLUMN_DESCRIPTION)));
            time.setText(mCursor.getString(mCursor.getColumnIndex(Contract.TABLE_NEWS.COLUMN_TIME)));
            /*
             * Picasso tutorial online
             * https://square.github.io/picasso/
             */
            Picasso.with(mContext).load(mCursor.getString(mCursor.getColumnIndex(Contract.TABLE_NEWS.COLUMN_IMAGE_URL))).resize(750, 550).into(imageView);
        }

        @Override
        public void onClick(View v) {
            int pos = getAdapterPosition();
            mListener.onItemClick(mCursor, pos);
        }
    }




}
