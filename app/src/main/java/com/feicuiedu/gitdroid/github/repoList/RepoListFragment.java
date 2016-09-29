package com.feicuiedu.gitdroid.github.repoList;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.feicuiedu.gitdroid.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.header.StoreHouseHeader;

public class RepoListFragment extends Fragment {

    @BindView(R.id.lvRepos)
    ListView lvRepos;
    @BindView(R.id.ptrClassicFrameLayout)
    PtrClassicFrameLayout ptrFrameLayout;
    @BindView(R.id.emptyView)
    TextView emptyView;
    @BindView(R.id.errorView)
    TextView errorView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_repo_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        RepoListAdapter adapter = new RepoListAdapter();
        lvRepos.setAdapter(adapter);

        // 初始化下拉刷新
        initPullToPresh();

    }

    private void initPullToPresh() {

        // 刷新时间间隔比较短，不触发刷新
        ptrFrameLayout.setLastUpdateTimeRelateObject(this);

        // 关闭Header所用时间
        ptrFrameLayout.setDurationToClose(1500);

        // 更改头布局
        StoreHouseHeader header = new StoreHouseHeader(getContext());
        header.initWithString("I LIKE ANDROID");
        header.setPadding(0,60,0,60);
        ptrFrameLayout.setHeaderView(header);
        ptrFrameLayout.addPtrUIHandler(header);

        ptrFrameLayout.setBackgroundResource(R.color.colorRefresh);

        // 设置刷新的监听，做刷新的操作（去进行数据请求）
        ptrFrameLayout.setPtrHandler(new PtrDefaultHandler() {

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {

                // TODO 去做数据的请求

            }
        });

    }
}
