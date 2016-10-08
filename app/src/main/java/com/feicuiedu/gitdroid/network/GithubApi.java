package com.feicuiedu.gitdroid.network;

import com.feicuiedu.gitdroid.github.repoList.model.RepoResult;
import com.feicuiedu.gitdroid.github.repoinfo.RepoContentResult;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface GithubApi {

    // Github 申请授权登录的信息
    public String CLIENT_ID = "a764de00ce4cdd8ed746";
    public String CLIENT_SECRET = "b5a3d14efb593ec0b5ca085c313f8d19270a241e";

    // 申请填写的标志（重定向标志）
    public String CALL_BACK = "feicuiedu";

    String AUTH_SCOPE = "user,public_repo,repo";

    // 登录页面的网址（WebView来进行访问）
    String AUTH_URL = "https://github.com/login/oauth/authorize?client_id"+CLIENT_ID+"&scope="+AUTH_SCOPE;


    /**
     * 获取仓库列表的请求Api
     *
     * @param query  查询的参数--体现为语言
     * @param pageId 查询页数--从1开始
     * @return
     */
    @GET("/search/repositories")
    Call<RepoResult> searchRepos(
            @Query("q") String query,
            @Query("page") int pageId);

    /**
     * 获取readme
     * @param owner 仓库拥有者
     * @param repo 仓库名称
     * @return
     */
    @GET("/repos/{owner}/{repo}/readme")
    Call<RepoContentResult> getReadme(@Path("owner") String owner,
                                      @Path("repo") String repo);

    /**
     * 获取MarkDown文件内容，内容以HTML形式展示出来
     * @param body
     * @return
     */
    @Headers({"Content-Type:text/plain"})
    @POST("/markdown/raw")
    Call<ResponseBody> markDown(@Body RequestBody body);



}
