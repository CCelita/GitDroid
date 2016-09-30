package com.feicuiedu.gitdroid.github.repoList.view;

import com.feicuiedu.gitdroid.github.repoList.model.Repo;
import com.feicuiedu.gitdroid.github.repoList.model.RepoResult;

import java.util.List;

/**
 * Created by 123 on 2016/9/30.
 */
public interface RepoLoadView {

    void showLoadingView();
    void hideLoadView();
    void showLoadError();
    void showMessage(String msg);
    void addLoadData(List<Repo> list);

}
