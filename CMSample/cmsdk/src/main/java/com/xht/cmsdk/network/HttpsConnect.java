package com.xht.cmsdk.network;

import com.xht.cmsdk.enums.HttpType;
import com.xht.cmsdk.enums.NetworkType;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.URL;
import java.security.SecureRandom;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

/**
 * Created by XIE on 2018/8/10.
 */

public class HttpsConnect implements IServerConnect {

    @Override
    public String getRequest(ConnParams params, Callback callback) {
        StringBuffer buffer = new StringBuffer();
        try {

            // 创建SSLContext对象，并使用我们指定的信任管理器初始化
            TrustManager[] tm = { new HTTPSTrustManager() };
            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
            sslContext.init(null, tm, new SecureRandom());

            // 从上述SSLContext对象中得到SSLSocketFactory对象
            SSLSocketFactory ssf = sslContext.getSocketFactory();

            //打开连接
            URL url = new URL(params.getConnUrl());
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.setSSLSocketFactory(ssf);
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setReadTimeout(TIMEOUT);
            connection.setConnectTimeout(TIMEOUT);
            connection.setUseCaches(false);

            // 设置请求方式（GET/POST）
            connection.setRequestMethod(HttpType.GET.toString());

            connection.connect();

            // 将返回的输入流转换成字符串
            InputStream inputStream = connection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String str = null;
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }
            bufferedReader.close();
            inputStreamReader.close();
            // 释放资源
            inputStream.close();
            inputStream = null;
            connection.disconnect();
        } catch (ConnectException ce) {
            ce.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return buffer.toString();
    }

    @Override
    public String postRequest(ConnParams params, Callback callback) {
        StringBuffer buffer = new StringBuffer();
        try {

            // 创建SSLContext对象，并使用我们指定的信任管理器初始化
            TrustManager[] tm = { new HTTPSTrustManager() };
            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
            sslContext.init(null, tm, new SecureRandom());

            // 从上述SSLContext对象中得到SSLSocketFactory对象
            SSLSocketFactory ssf = sslContext.getSocketFactory();

            //打开连接
            URL url = new URL(params.getConnUrl());
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.setSSLSocketFactory(ssf);
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setReadTimeout(TIMEOUT);
            connection.setConnectTimeout(TIMEOUT);
            connection.setUseCaches(false);

            // 设置请求方式（GET/POST）
            connection.setRequestMethod(HttpType.POST.toString());

            // 当有数据需要提交时
            OutputStream outputStream = connection.getOutputStream();
            // 注意编码格式，防止中文乱码
            outputStream.write(params.getParams().getBytes("utf-8"));
            outputStream.close();

            // 将返回的输入流转换成字符串
            InputStream inputStream = connection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String str = null;
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }
            bufferedReader.close();
            inputStreamReader.close();
            // 释放资源
            inputStream.close();
            inputStream = null;
            connection.disconnect();
        } catch (ConnectException ce) {
            ce.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return buffer.toString();
    }
}
