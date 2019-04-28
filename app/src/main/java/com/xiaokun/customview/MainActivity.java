package com.xiaokun.customview;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.xiaokun.customview.refresh_layout.RefreshLayout;

public class MainActivity extends AppCompatActivity {

    private RefreshLayout refreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        refreshLayout = findViewById(R.id.refresh_layout);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new MyAdapter(this));

        refreshLayout.setOnRefresh(new RefreshLayout.OnRefresh() {
            @Override
            public void onDownPullRefresh() {
                new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        super.handleMessage(msg);
                        switch (msg.what) {
                            case 0:
                                refreshLayout.downPullFinish();
                        }
                    }
                }.sendEmptyMessageDelayed(0, 2000);
            }

            @Override
            public void onUpPullRefresh() {
                new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        super.handleMessage(msg);
                        switch (msg.what) {
                            case 1:
                                refreshLayout.upPullFinish();
                        }
                    }
                }.sendEmptyMessageDelayed(1, 2000);
            }
        });
    }

    class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyHolder> {
        private final LayoutInflater mInflater;
        private Context mContext;

        public MyAdapter(Context context) {
            mContext = context;
            mInflater = LayoutInflater.from(mContext);
        }

        @NonNull
        @Override
        public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = mInflater.inflate(R.layout.item_list, parent, false);
            return new MyHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MyHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return 20;
        }

        class MyHolder extends RecyclerView.ViewHolder {
            public MyHolder(View itemView) {
                super(itemView);
            }
        }
    }


}
