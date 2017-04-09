package com.aroliant.endlessrecyclerview;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import java.util.ArrayList;


/**
 * Created by Jacob Samro on 09-Apr-16.
 */



public class EndlessRecyclerViewAdapter extends RecyclerView.Adapter<EndlessRecyclerViewAdapter.ViewHolder> {

    Context context;
    ArrayList<String> names = new ArrayList<String>();
    ArrayList<Integer> count = new ArrayList<Integer>();


    private int visibleThreshold = 5;
    private int lastVisibleItem, totalItemCount;
    private static boolean loading = false;
    private OnLoadMoreListener onLoadMoreListener;


    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView mTextViewName;
        public TextView mTextViewCount;

        public ViewHolder(View v) {
            super(v);

            mTextViewName = (TextView) v.findViewById(R.id.name);
            mTextViewCount = (TextView) v.findViewById(R.id.count);

        }


    }


    public EndlessRecyclerViewAdapter(Context context,
                                      RecyclerView recyclerView,
                                      ArrayList<String> names,
                                      ArrayList<Integer> count) {
        this.context = context;
        this.names = names;
        this.count = count;

        if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {

            final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView
                    .getLayoutManager();


            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                        @Override
                        public void onScrolled(RecyclerView recyclerView,
                                               int dx, int dy) {
                            super.onScrolled(recyclerView, dx, dy);


                            totalItemCount = linearLayoutManager.getItemCount();

                            lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();

                            Log.d("...","TOTAL " + totalItemCount);
                            Log.d("...","lastVisibleItem " + lastVisibleItem);

                            if (!loading
                                    && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                                // End has been reached

                                // Do something
                                if (onLoadMoreListener != null) {
                                    onLoadMoreListener.onLoadMore();

                                }
                                loading = true;

                            }
                        }
                    });
        }
    }

    @Override
    public EndlessRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                            int viewType) {

        View v =  LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_recyclerview, parent, false);

        ViewHolder vh = new ViewHolder(v);


        return vh;
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        holder.mTextViewName.setText(names.get(position));
        holder.mTextViewCount.setText(count.get(position) + "");

    }


    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }


    @Override
    public int getItemCount() {
        return names.size();

    }

    public void setLoaded() {
        loading = false;
    }
}
