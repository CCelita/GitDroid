package com.feicuiedu.gitdroid.login;

import android.util.Log;
import android.widget.Toast;

import com.feicuiedu.gitdroid.login.model.AccessToken;
import com.feicuiedu.gitdroid.network.GithubApi;
import com.feicuiedu.gitdroid.network.GithubClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by 123 on 2016/10/8.
 */
public class LoginPresenter {


    private Call<AccessToken> tokenCall;

    /**
     * 登录的业务
     * 1. 使用code 获取Token
     */
    public void login(String code){

        if (tokenCall!=null){
            tokenCall.cancel();
        }

        // 利用code 去获取Token
        tokenCall = GithubClient.getInstance().getOAuthToken(
                GithubApi.CLIENT_ID,
                GithubApi.CLIENT_SECRET,
                code);

        tokenCall.enqueue(tokenCallback);
    }

    private Callback<AccessToken> tokenCallback = new Callback<AccessToken>() {

        @Override
        public void onResponse(Call<AccessToken> call, Response<AccessToken> response) {
            if (response.isSuccessful()) {

                // 得到Token值
                AccessToken accessToken = response.body();
                String token = accessToken.getAccessToken();

                Log.w("TAG","token 的值："+token);



            }

        }

        @Override
        public void onFailure(Call<AccessToken> call, Throwable t) {

        }
    };
}
