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
import com.feicuiedu.gitdroid.commons.ActivityUtils;
import com.feicuiedu.gitdroid.github.Language;
import com.feicuiedu.gitdroid.github.repoList.model.Repo;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.header.StoreHouseHeader;

public class RepoListFragment extends Fragment implements RepoRefreshView{

    @BindView(R.id.lvRepos)
    ListView lvRepos;
    @BindView(R.id.ptrClassicFrameLayout)
    PtrClassicFrameLayout ptrFrameLayout;
    @BindView(R.id.emptyView)
    TextView emptyView;
    @BindView(R.id.errorView)
    TextView errorView;

    private ActivityUtils activityUtils;

    private static final String KEY_LABGUAGE = "key_language";
    private RepoListAdapter adapter;

    private RepoListPresenter presenter;

    // 提供一个创建方法，进行数据（Language）的传递。
    public static RepoListFragment getInstance(Language language){

        RepoListFragment repoListFragment = new RepoListFragment();

        // Bundle 传递数据
        Bundle bundle = new Bundle();
        bundle.putSerializable(KEY_LABGUAGE,language);
        repoListFragment.setArguments(bundle);
        return repoListFragment;
    }

    private Language getLanguage(){
        return (Language) getArguments().getSerializable(KEY_LABGUAGE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View inflate = inflater.inflate(R.layout.fragment_repo_list, container, false);
        ButterKnife.bind(this,inflate);
        return inflate;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        activityUtils = new ActivityUtils(this);
        presenter = new RepoListPresenter(this,getLanguage());

        adapter = new RepoListAdapter();
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

                // 去做数据的请求
                presenter.refresh();
            }
        });

    }

    // --------------------刷新的视图------------------------------
    @Override
    public void stopRefresh() {
        ptrFrameLayout.refreshComplete();// 停止刷新
    }

    @Override
    public void showMessage(String msg) {
        activityUtils.showToast(msg);
    }

    @Override
    public void refreshData(List<Repo> repoList) {
        adapter.clear();
        adapter.addAll(repoList);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showEmptyView() {
        ptrFrameLayout.setVisibility(View.GONE);
        emptyView.setVisibility(View.VISIBLE);
        errorView.setVisibility(View.GONE);
    }

    @Override
    public void showContentView() {
        ptrFrameLayout.setVisibility(View.VISIBLE);
        emptyView.setVisibility(View.GONE);
        errorView.setVisibility(View.GONE);
    }

    @Override
    public void showErrorView() {
        ptrFrameLayout.setVisibility(View.GONE);
        emptyView.setVisibility(View.GONE);
        errorView.setVisibility(View.VISIBLE);
    }
}
