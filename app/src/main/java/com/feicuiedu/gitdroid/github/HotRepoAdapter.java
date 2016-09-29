package com.feicuiedu.gitdroid.github;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.feicuiedu.gitdroid.github.repoList.RepoListFragment;

/**
 * Created by 123 on 2016/9/29.
 */
public class HotRepoAdapter extends FragmentPagerAdapter{

    public HotRepoAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return new RepoListFragment();
    }

    @Override
    public int getCount() {
        return 10;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return "java"+position;
    }
}
