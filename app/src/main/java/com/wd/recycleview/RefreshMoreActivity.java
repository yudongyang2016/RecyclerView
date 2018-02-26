package com.wd.recycleview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;

import com.wd.recycleview.hf.HeaderAndFooterRecyclerView;
import com.wd.recycleview.hf.LinearVerticalAdapter;
import com.wd.recycleview.hf.LoadMoreFooter;
import com.wd.recycleview.hfutil.ApiUtils;
import com.wd.recycleview.hfutil.HandlerUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RefreshMoreActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener, LoadMoreFooter.OnLoadMoreListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout refreshLayout;

    @BindView(R.id.recycler_view)
    HeaderAndFooterRecyclerView recyclerView;

    private LoadMoreFooter loadMoreFooter;
    private LinearVerticalAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refresh_and_load_more);
        ButterKnife.bind(this);
        toolbar.setTitle("Refresh and Load more");

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        new VerticalHeader(this, recyclerView);
        loadMoreFooter = new LoadMoreFooter(this, recyclerView, this);

        adapter = new LinearVerticalAdapter(this);
        recyclerView.setAdapter(adapter);

        refreshLayout.setColorSchemeResources(R.color.colorAccent);
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setRefreshing(true);
        onRefresh();
    }

    @Override
    public void onRefresh() {
        HandlerUtils.HANDLER.postDelayed(new Runnable() {

            @Override
            public void run() {
                adapter.getPictureList().clear();
                adapter.getPictureList().addAll(ApiUtils.buildPictureList());
                adapter.notifyDataSetChanged();
                refreshLayout.setRefreshing(false);
                loadMoreFooter.setState(LoadMoreFooter.STATE_ENDLESS);
            }

        }, 1000);
    }

    @Override
    public void onLoadMore() {
        HandlerUtils.HANDLER.postDelayed(new Runnable() {

            @Override
            public void run() {
                int startPosition = adapter.getItemCount();
                adapter.getPictureList().addAll(ApiUtils.buildPictureList());
                adapter.notifyItemRangeInserted(startPosition, ApiUtils.PAGE_SIZE);
                loadMoreFooter.setState(adapter.getItemCount() >= 30 ? LoadMoreFooter.STATE_FINISHED : LoadMoreFooter.STATE_ENDLESS);
            }

        }, 1000);
    }

}
