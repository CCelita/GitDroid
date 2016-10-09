package com.feicuiedu.gitdroid;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.feicuiedu.gitdroid.commons.ActivityUtils;
import com.feicuiedu.gitdroid.favorite.FavoriteFragment;
import com.feicuiedu.gitdroid.gank.GankFragment;
import com.feicuiedu.gitdroid.github.HotRepoFragment;
import com.feicuiedu.gitdroid.github.hotuser.HotUserFragment;
import com.feicuiedu.gitdroid.login.LoginActivity;
import com.feicuiedu.gitdroid.login.model.UserRepo;
import com.nostra13.universalimageloader.core.ImageLoader;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.navigationView)
    NavigationView navigationView;
    @BindView(R.id.drawerLayout)
    DrawerLayout drawerLayout;
    private Button btnLogin;
    private ImageView ivIcon;

    private ActivityUtils activityUtils;


    private HotRepoFragment hotRepoFragment;
    private HotUserFragment hotUserFragment;
    private FavoriteFragment favoriteFragment;
    private GankFragment gankFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 会触发onContentChanged方法
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        ButterKnife.bind(this);
        activityUtils = new ActivityUtils(this);

        // 处理toolbar作为ActionBar
        setSupportActionBar(toolbar);

        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        toggle.syncState();
        drawerLayout.addDrawerListener(toggle);

        btnLogin = ButterKnife.findById(navigationView.getHeaderView(0), R.id.btnLogin);
        ivIcon = ButterKnife.findById(navigationView.getHeaderView(0), R.id.ivIcon);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activityUtils.startActivity(LoginActivity.class);
                finish();
            }
        });

        hotRepoFragment = new HotRepoFragment();
        replaceFragment(hotRepoFragment);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (UserRepo.isEmpty()){
            btnLogin.setText(R.string.login_github);
            return;
        }
        // 切换账号按钮
        btnLogin.setText(R.string.switch_account);
        //Main页面的标题
        getSupportActionBar().setTitle(UserRepo.getUser().getName());
        // 设置头像
        ImageLoader.getInstance().displayImage(UserRepo.getUser().getAvatar(),ivIcon);
    }

    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container,fragment);
        fragmentTransaction.commit();
    }

    // 侧滑菜单监听器
    @Override public boolean onNavigationItemSelected(MenuItem item) {
        // 将默认选中项“手动”设置为false
        if (item.isChecked()) {
            item.setChecked(false);
        }
        // 根据选择做切换
        switch (item.getItemId()) {
            // 热门仓库
            case R.id.github_hot_repo:
                if (!hotRepoFragment.isAdded()) {
                    replaceFragment(hotRepoFragment);
                }
                break;
            // 热门开发者
            case R.id.github_hot_coder:
                if (hotUserFragment == null) hotUserFragment = new HotUserFragment();
                if (!hotUserFragment.isAdded()) {
                    replaceFragment(hotUserFragment);
                }
                break;
            // 我的收藏
            case R.id.arsenal_my_repo:
                if (favoriteFragment==null){
                    favoriteFragment = new FavoriteFragment();
                }
                if (!favoriteFragment.isAdded()){
                    replaceFragment(favoriteFragment);
                }
                break;
            // 每日干货
            case R.id.tips_daily:
                if (gankFragment==null){
                    gankFragment = new GankFragment();
                }
                if (!gankFragment.isAdded()){
                    replaceFragment(gankFragment);
                }
                break;
        }
        // 关闭drawerLayout

        drawerLayout.closeDrawer(GravityCompat.START);

        // 返回true，代表将该菜单项变为checked状态
        return true;
    }
}
