package com.tangdi.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

public class MainActivity extends AppCompatActivity implements BGARefreshLayout.BGARefreshLayoutDelegate {


    private BGARefreshLayout bgaRefreshLayout;

    private RecyclerView recyclerView;

    private ArrayList<String> arrayList = new ArrayList<>();

    private NewsRecycleAdapter newsRecycleAdapter;

    private Timer timer = new Timer();

    private TimerTask timerTask = new TimerTask() {
        @Override
        public void run() {
            bgaRefreshLayout.endLoadingMore();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.recy_view);
        bgaRefreshLayout = (BGARefreshLayout)findViewById(R.id.refresh_layout);
        initView();
        initRefreshLayout(bgaRefreshLayout);
    }

    private void initRefreshLayout(BGARefreshLayout refreshLayout) {
        // 为BGARefreshLayout 设置代理
        refreshLayout.setDelegate(this);
        // 设置下拉刷新和上拉加载更多的风格     参数1：应用程序上下文，参数2：是否具有上拉加载更多功能
        BGANormalRefreshViewHolder refreshViewHolder = new BGANormalRefreshViewHolder(this, true);

        //refreshLayout.setPullDownRefreshEnable(true);


        // 为了增加下拉刷新头部和加载更多的通用性，提供了以下可选配置选项  -------------START
        // 设置正在加载更多时不显示加载更多控件
        // mRefreshLayout.setIsShowLoadingMoreView(false);
        // 设置正在加载更多时的文本
        refreshViewHolder.setLoadingMoreText("测试文字");
        // 设置整个加载更多控件的背景颜色资源 id
        refreshViewHolder.setLoadMoreBackgroundColorRes(R.color.colorAccent);
        // 设置整个加载更多控件的背景 drawable 资源 id
        //refreshViewHolder.setLoadMoreBackgroundDrawableRes(R.mipmap.ic_launcher);

        // 设置下拉刷新控件的背景颜色资源 id
        refreshViewHolder.setRefreshViewBackgroundColorRes(R.color.colorAccent);
        // 设置下拉刷新控件的背景 drawable 资源 id
        //refreshViewHolder.setRefreshViewBackgroundDrawableRes(R.mipmap.ic_default_adimage);

        //refreshViewHolder.setPullDownImageResource(R.mipmap.ic_default_adimage);
        // 设置自定义头部视图（也可以不用设置）     参数1：自定义头部视图（例如广告位）， 参数2：上拉加载更多是否可用
        //refreshLayout.setCustomHeaderView(mBanner, false);
        // 可选配置  -------------END

        //refreshViewHolder.setChangeToReleaseRefreshAnimResId(R.mipmap.ic_default_adimage);

        //refreshViewHolder.setRefreshingAnimResId(R.mipmap.ic_default_adimage);

        // 设置下拉刷新和上拉加载更多的风格
        refreshLayout.setRefreshViewHolder(refreshViewHolder);
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        arrayList.clear();
        for(int i = 0 ; i < 40; i++){
            arrayList.add(i + "");
        }
        newsRecycleAdapter.notifyDataSetChanged();
        refreshLayout.endRefreshing();
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        for(int i = 0 ; i < 40; i++){
            arrayList.add(new Random().nextInt() + "");
            newsRecycleAdapter.notifyDataSetChanged();
        }
        timer.schedule(timerTask, 2000);
        return true;
    }

    private void initView(){
        newsRecycleAdapter = new NewsRecycleAdapter(arrayList, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(newsRecycleAdapter);
    }
}
