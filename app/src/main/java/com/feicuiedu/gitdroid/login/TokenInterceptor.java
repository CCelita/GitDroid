package com.feicuiedu.gitdroid.login;

import com.feicuiedu.gitdroid.login.model.UserRepo;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by 123 on 2016/10/8.
 */

// 主要是将我们的Token添加到请求头里面
public class TokenInterceptor implements Interceptor{

    @Override
    public Response intercept(Chain chain) throws IOException {

        Request request = chain.request();
        Request.Builder builder = request.newBuilder();

        if (UserRepo.hasAccessToken()){
            builder.header("Authorization","token "+UserRepo.getAccessToken());
        }
        Response response = chain.proceed(builder.build());
        if (response.isSuccessful()){
            return response;
        }
        if (response.code()==401 || response.code()==403){
            throw new IOException("未经授权的！");
        }else {
            throw  new IOException("响应码："+response.code());
        }
    }
}
