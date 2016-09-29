package com.feicuiedu.gitdroid.login;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.feicuiedu.gitdroid.R;
import com.feicuiedu.gitdroid.commons.ActivityUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.droidsonroids.gif.GifImageView;


/**
 * 1.使用WebView来加载登录网址，实现登录账户的填写及登录
 * 2.是否同意授权，如果同意授权，重定向另一个URL，会有一个临时授权码code
 * 3.拿到临时授权码之后，使用API来获得用户Token
 * 4.获得用户Token之后，访问user,public_repo,repo，主要为了拿到用户信息展示出来
 */
public class LoginActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
    }
}
