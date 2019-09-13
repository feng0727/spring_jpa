package com.ccb.domain.utils;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class HttpClientUtils {

    private static final Log LOG = LogFactory.getLog(HttpClientUtils.class);

    private static RequestConfig requestConfig = null;

    static
    {
        // 设置请求和传输超时时间
        requestConfig = RequestConfig.custom().setSocketTimeout(2000).setConnectTimeout(2000).build();
    }
    /**
     * post请求
     * 解决没有响应信息（是因为之前没有释放连接，导致获取不到响应）
     * @param uri
     * @param body
     */
    public static Map<String, String> post(String uri, JSONObject header, JSONObject body, Integer timeout) {
        Map<String, String> retMap = new HashMap<>();
        // 创建请求对象
        CloseableHttpClient httpClient =  HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(uri);
        httpPost.setConfig(requestConfig);
        try {
            // 设置请求头信息
            httpPost.addHeader(HTTP.CONTENT_TYPE, "application/json;charset=utf-8");
            if (header != null) {
                for (String key : header.keySet()) {
                    Object value = header.get(key);
                    if (value != null && StringUtils.isNotBlank(value.toString())) {
                        httpPost.addHeader(key, (value == null) ? null : String.valueOf(value));
                    }
                }
            }
            // 设置请求体信息
            String jsonstr = com.alibaba.fastjson.JSON.toJSONString(body);
            StringEntity entity = new StringEntity(jsonstr,"utf-8");
            entity.setContentEncoding("UTF-8");
            entity.setContentType("application/json");
            httpPost.setEntity(entity);

            // 发出post请求
            CloseableHttpResponse response = httpClient.execute(httpPost);
            //解析响应结果
            retMap.put("status", "success");
            int statusCode = response.getStatusLine().getStatusCode();
            retMap.put("statusCode", statusCode + "");
            if (statusCode == 200) {
                String result = EntityUtils.toString(response.getEntity(),"utf-8");
                retMap.put("result", result);
            } else {
                retMap.put("result", EntityUtils.toString(response.getEntity(),"utf-8"));
            }

        } catch (Exception e) {
            LOG.error(ExceptionUtils.getFullStackTrace(e));
            retMap.put("status", "failed");
            retMap.put("exMsg", e.getLocalizedMessage());
        } finally {
            httpPost.releaseConnection();
        }
        return  retMap;
    }



    public static Map<String, String> post(String uri, JSONObject header, String body, Integer timeout) {
        Map<String, String> retMap = new HashMap<>();
        // 创建请求对象
        CloseableHttpClient httpClient =  HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(uri);
        httpPost.setConfig(requestConfig);
        try {

            // 设置请求头信息
            httpPost.addHeader(HTTP.CONTENT_TYPE, "application/json;charset=utf-8");
            if (header != null) {
                for (String key : header.keySet()) {
                    Object value = header.get(key);
                    if (value != null && StringUtils.isNotBlank(value.toString())) {
                        httpPost.addHeader(key, (value == null) ? null : String.valueOf(value));
                    }
                }
            }
            // 设置请求体信息
            StringEntity entity = new StringEntity(body,"utf-8");
            //entity.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
            entity.setContentEncoding("UTF-8");
            entity.setContentType("application/json");
            httpPost.setEntity(entity);

            // 设置超时时间
/*            if (timeout != null) {
                httpPost.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, timeout);
            }*/
            // 发出post请求
            CloseableHttpResponse response = httpClient.execute(httpPost);
            //解析响应结果
            retMap.put("status", "success");
            int statusCode = response.getStatusLine().getStatusCode();
            retMap.put("statusCode", statusCode + "");
            if (statusCode == 200) {
                String result = EntityUtils.toString(response.getEntity(),"utf-8");
                retMap.put("result", result);
            } else {
                retMap.put("result", EntityUtils.toString(response.getEntity(),"utf-8"));
            }

        } catch (Exception e) {
            LOG.error(ExceptionUtils.getFullStackTrace(e));
            retMap.put("status", "failed");
            retMap.put("exMsg", e.getLocalizedMessage());
        } finally {
            httpPost.releaseConnection();
        }
        return  retMap;
    }


    /**
     * get请求
     *
     * @param uri
     * @param body
     */
    public static Map<String, String> get(String uri, JSONObject header, JSONObject body) {
        return get(uri, header, body, null);
    }

    /**
     * get请求
     *
     * @param uri
     * @param body
     */
    public static Map<String, String> get(String uri, JSONObject header, JSONObject body, Integer timeout) {
        Map<String, String> retMap = new HashMap<>();
        HttpGet request = new HttpGet(uri);
        try {
            HttpClient client = HttpClients.createDefault();//创建HttpClient对象

            if (header != null) {
                for (String key : header.keySet()) {
                    Object value = header.get(key);
                    if (value != null && StringUtils.isNotBlank(value.toString())) {
                        request.addHeader(key, (value == null) ? null : String.valueOf(value));
                    }
                }
            }
            HttpResponse response = client.execute(request);//发送get请求
            int statusCode = response.getStatusLine().getStatusCode();

            retMap.put("status", "success");
            retMap.put("statusCode", statusCode + "");

            /**请求发送成功，并得到响应**/
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                /**读取服务器返回过来的json字符串数据**/
                String strResult = EntityUtils.toString(response.getEntity(), "UTF-8");
                retMap.put("result", strResult);
            } else {
                retMap.put("result", EntityUtils.toString(response.getEntity()));
            }
        } catch (IOException e) {

            LOG.debug(ExceptionUtils.getFullStackTrace(e));
            retMap.put("status", "failed");
            retMap.put("exMsg", e.getLocalizedMessage());
            e.printStackTrace();
        } finally {
            request.releaseConnection();
        }
        return retMap;
    }

}
