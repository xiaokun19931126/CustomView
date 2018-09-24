package com.xiaokun.customview;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        RefreshAndLoadMoreView view = (RefreshAndLoadMoreView) findViewById(R.id.refresh_load_view);
//        TextView textView = new TextView(this);
//        textView.setText("内容布局");
//        textView.setGravity(Gravity.CENTER);
//        textView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(MainActivity.this, "dianji", Toast.LENGTH_SHORT).show();
//            }
//        });
//        view.setContentView(textView);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new MyAdapter(this));
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
