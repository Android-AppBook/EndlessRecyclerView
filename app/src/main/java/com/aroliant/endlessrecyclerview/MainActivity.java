package com.aroliant.endlessrecyclerview;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> names = new ArrayList<String>();

    int offset = 0;

    RecyclerView recyclerView;
    EndlessRecyclerViewAdapter mAdapter;


    protected Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        handler = new Handler();

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        final LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);


        recyclerView.setLayoutManager(mLayoutManager);

        recyclerView.setHasFixedSize(true);



        mAdapter = new EndlessRecyclerViewAdapter(this,
                recyclerView,
                names);

        recyclerView.setAdapter(mAdapter);

        mAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {


                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        loadDataOffset(offset);
                        offset++;

                    }
                }, 10);



            }
        });

        loadDataOffset(offset);


    }

    void loadDataOffset(int _offset){


        int i = _offset * 10;

        Log.d("...",i + "");

        if(i == 0){
            i = 0;
        }

        for (int j = i ; j < (i + 10) ; j++ ){
            names.add("Name " + j);
            Log.d("...", j + "");
        }

        try{
            mAdapter.notifyDataSetChanged();
            mAdapter.setLoaded();
        }catch (Exception e){
            e.printStackTrace();
        }



    }
}
