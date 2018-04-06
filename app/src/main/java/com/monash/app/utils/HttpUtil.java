package com.monash.app.utils;

/**
 * Created by abner on 2018/4/5.
 */

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by abner on 2018/4/5.
 */
public class HttpUtil {

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    public static final MediaType XML = MediaType.parse("application/xml; charset=utf-8");
    private static HttpUtil instance;
    private OkHttpClient client;
    private String result;

    private HttpUtil() {
        client = new OkHttpClient();
        result = "";
    }

    public static HttpUtil getInstance() {
        return instance == null ? instance = new HttpUtil() : instance;
    }

    /**
     * 提交POST请求
     *
     * @param url       地址
     * @param mediaType 请求体文本类型
     * @param content   请求体
     * @param headers   请求头
     * @return
     * @throws IOException
     */
    public String post(String url, MediaType mediaType, String content, String... headers) throws IOException {
        RequestBody body = RequestBody.create(mediaType, content);
        Request.Builder builder = new Request.Builder().url(url);
        if (headers != null && headers.length > 0 && headers.length % 2 == 0) {
            for (int i = 0; i < headers.length - 1; i++)
                if (i % 2 == 0) builder.addHeader(headers[i], headers[i + 1]);
        }
        Request request = builder.post(body).build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    /**
     * 提交POST请求
     *
     * @param url     地址
     * @param content 请求体
     * @return
     * @throws IOException
     */
    public String post(String url, String content) throws IOException {
        return post(url, HttpUtil.JSON, content, null);
    }

    /**
     * 提交GET请求
     *
     * @param url 地址
     * @return
     */
    public String get(final String url){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Request request = new Request.Builder().url(url).build();
                    Response response = client.newCall(request).execute();
                    setResult(response.body().string());
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }).start();

        return getResult();
    }

    private void setResult(String str){
        result = str;
    }

    private String getResult(){
        return result;
    }
}