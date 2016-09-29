package com.feicuiedu.gitdroid.github;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.feicuiedu.gitdroid.github.repoList.RepoListFragment;

import java.util.List;

/**
 * Created by 123 on 2016/9/29.
 */
public class HotRepoAdapter extends FragmentPagerAdapter{

    private List<Language> list;

    public HotRepoAdapter(FragmentManager fm, Context context) {
        super(fm);
        list = Language.getLanguages(context);
    }

    @Override
    public Fragment getItem(int position) {
        return new RepoListFragment();
    }

    @Override
    public int getCount() {
        return list==null?0:list.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return list.get(position).getName();
    }
}
