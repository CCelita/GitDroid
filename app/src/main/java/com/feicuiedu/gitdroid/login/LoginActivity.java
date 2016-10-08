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
import android.widget.Toast;

import com.feicuiedu.gitdroid.R;
import com.feicuiedu.gitdroid.network.GithubApi;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.droidsonroids.gif.GifImageView;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.webView)
    WebView webView;
    @BindView(R.id.gifImageView)
    GifImageView gifImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initWebView();
    }

    //WebView的设置
    private void initWebView() {

        // 删除所有的Cookie, 主要是为了清除登录信息
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.removeAllCookie();

        // 加载登录网页
        webView.loadUrl(GithubApi.AUTH_URL);

        // 让WebView获取焦点
        webView.setFocusable(true);
        webView.setFocusableInTouchMode(true);

        // 设置WebView的进度监听，加载完成之前显示动画，完成之后，动画隐藏
        webView.setWebChromeClient(webChormeClkient);

        // 监听WebView的页面刷新
        webView.setWebViewClient(webViewClient);

    }

    private WebViewClient webViewClient = new WebViewClient(){
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            Uri uri = Uri.parse(url);
            if (GithubApi.CALL_BACK.equals(uri.getScheme())){
                String code = uri.getQueryParameter("code");
                Toast.makeText(LoginActivity.this, "code:"+code, Toast.LENGTH_SHORT).show();
                //TODO code拿到之后，利用code来进行token的获取
                new LoginPresenter().login(code);
            }

            return super.shouldOverrideUrlLoading(view, url);
        }
    };

    private WebChromeClient webChormeClkient = new WebChromeClient(){

        @Override
        public void onProgressChanged(WebView view, int newProgress) {

            if (newProgress>=100){
                gifImageView.setVisibility(View.GONE);
            }
        }
    };
}
