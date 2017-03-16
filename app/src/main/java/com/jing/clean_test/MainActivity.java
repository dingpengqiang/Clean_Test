package com.jing.clean_test;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.jing.clean.R;
import com.jing.clean_test.adapter.MyAdapter;
import com.jing.clean_test.api.ApiUrl;
import com.jing.clean_test.model.JavaBean;
import com.jing.clean_test.model.RowsBean;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private RecyclerView mRecyclerView;
    private MyAdapter adapter;
    private TextView mTitle;
    private SwipeRefreshLayout mRefresh;
    private boolean isFrist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //初始化View
        initView();

    }

    private void initView() {
        mTitle = (TextView) findViewById(R.id.title_action);

        mRefresh = (SwipeRefreshLayout) findViewById(R.id.refresh);
        mRefresh.setOnRefreshListener(this);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        adapter = new MyAdapter(R.layout.item,null);

        //加载空布局
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.empty, null);
        adapter.setEmptyView(view);

        mRecyclerView.setAdapter(adapter);
    }

    /**
     * 网络请求数据
     */
    private void getData(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://thoughtworks-ios.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiUrl service = retrofit.create(ApiUrl.class);
        Call<JavaBean> call = service.getApiUrl();
        call.enqueue(new Callback<JavaBean>() {
            @Override
            public void onResponse(Call<JavaBean> call, Response<JavaBean> response) {
                JavaBean body = response.body();

                //设置标题
                String title = body.getTitle();
                mTitle.setText(title);

                //设置Item内容
                List<RowsBean> rows = body.getRows();

                //遍历判断item的标题是不是空，如果为空，直接移除
                for (int i = 0; i < rows.size(); i++) {
                    if (TextUtils.isEmpty(rows.get(i).getTitle())){
                        rows.remove(i);
                    }
                }
                //加载数据源
                if (isFrist){
                    adapter.notifyDataSetChanged();
                }else {
                    adapter.addData(rows);
                    isFrist = true;
                }

                Toast.makeText(MainActivity.this, "加载成功！", Toast.LENGTH_SHORT).show();
                mRefresh.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<JavaBean> call, Throwable t) {
                Log.e(TAG, "onFailure: "+t.getMessage() );
                Toast.makeText(MainActivity.this, "加载失败！", Toast.LENGTH_SHORT).show();
                mRefresh.setRefreshing(true);
            }
        });
    }

    @Override
    public void onRefresh() {
        //加载数据
        getData();
    }
}
