package com.feicuiedu.gitdroid.github.repoList;

import com.feicuiedu.gitdroid.github.Language;
import com.feicuiedu.gitdroid.github.repoList.model.Repo;
import com.feicuiedu.gitdroid.github.repoList.model.RepoResult;
import com.feicuiedu.gitdroid.network.GithubClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by 123 on 2016/9/30.
 */

// 业务类，主要做数据的请求
public class RepoListPresenter {
    private Language language;
    private int nextPage = 1;
    private RepoRefreshView refreshView;

    public RepoListPresenter(RepoRefreshView refreshView,Language language) {
        this.refreshView = refreshView;
        this.language = language;
    }

    public void refresh(){

        // 显示刷新视图
        refreshView.showContentView();

        nextPage = 1;
        Call<RepoResult> refreshCall = GithubClient.getInstance().searchRepos("language:" + language.getPath(), nextPage);
        refreshCall.enqueue(refreshCallback);
    }

    private Callback<RepoResult> refreshCallback = new Callback<RepoResult>() {

        // 响应成功
        @Override
        public void onResponse(Call<RepoResult> call, Response<RepoResult> response) {
            // 停止刷新
            refreshView.stopRefresh();

            // 响应成功
            if (response.isSuccessful()){
                RepoResult repoResult = response.body();

                // 判断数据是不是空的
                if (repoResult==null){
                    refreshView.showEmptyView();
                    return;
                }

                if (repoResult.getTotalCount()<=0){
                    refreshView.showEmptyView();
                    refreshView.refreshData(null);
                    return;
                }
                // 有数据，设置数据
                List<Repo> repoList = repoResult.getRepoList();
                if (repoList!=null){
                    refreshView.refreshData(repoList);// 设置刷新的数据
                    // 下拉刷新完之后，第一页数据请求完成，下次上拉加载，从第二页开始
                    nextPage = 2;
                    return;
                }
            }
            refreshView.showErrorView();
        }

        // 失败
        @Override
        public void onFailure(Call<RepoResult> call, Throwable t) {
            refreshView.stopRefresh();
            refreshView.showMessage("请求失败"+t.getMessage());
        }
    };
}
