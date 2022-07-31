package com.darren.es;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.*;

/**
 * <h3>mybatis</h3>
 * <p></p>
 *
 * @author : Darren
 * @date : 2022年07月31日 08:14:46
 **/
public class Invoker {

    public static void main(String[] args) {
        EsRequest esRequest = new EsRequest();
        String sql = "SELECT * FROM test WHERE name like 'John Doe%' order by name";
        esRequest.setSql(sql);
        esRequest.setFetchSize(3);
        invoke(esRequest);
    }


    /**
     * https://blog.csdn.net/qq_41903017/article/details/116529206
     */
    public static void invoke(EsRequest esRequest) {
        // 获取ES配置
        EsConfiguration esConfiguration = getEsConfiguration();
        // 获取headers
        Map<String, String> headers = getHeaders(esConfiguration.getUsername(), esConfiguration.getPassword());
        // 获取body
        JSONObject requestBody = getRequestBody(esRequest);
        // 发送post请求并接收响应数据
        String body = HttpUtil.createPost(esConfiguration.getUrl()).addHeaders(headers).body(requestBody).execute().body();
        System.out.println(body);
        List<Map<String, String>> data = new ArrayList<>();
        EsResponse esResponse = com.alibaba.fastjson.JSONObject.parseObject(body, EsResponse.class);
        List columns = esResponse.getColumns();
        handleRows(data, columns, esResponse);
        esResponse.setRows(data);
        esResponse.setCursor(null);
        System.out.println(esResponse);
    }

    private static void handleRows(List<Map<String, String>> data, List columns, EsResponse esResponse){
        com.alibaba.fastjson.JSONArray rows = (com.alibaba.fastjson.JSONArray) esResponse.getRows();
        for (int i = 0; i < rows.size(); i++) {
            Map<String, String> map = new HashMap<>();
            com.alibaba.fastjson.JSONArray row = rows.getJSONArray(i);
            for (int j = 0; j < row.size(); j++) {
                EsResponse.Column o = (EsResponse.Column) columns.get(j);
                map.put(o.getName(), row.getString(j));
            }
            data.add(map);
        }

        if (rows.size() != 0 && StrUtil.isNotBlank(esResponse.getCursor())){
            // 获取ES配置
            EsConfiguration esConfiguration = getEsConfiguration();
            // 获取headers
            Map<String, String> headers = getHeaders(esConfiguration.getUsername(), esConfiguration.getPassword());
            // 获取body
            JSONObject requestBody = new JSONObject();
            requestBody.put("cursor", esResponse.getCursor());
            String body = HttpUtil.createPost(esConfiguration.getUrl()).addHeaders(headers).body(requestBody).execute().body();
            esResponse = com.alibaba.fastjson.JSONObject.parseObject(body, EsResponse.class);
            handleRows(data, columns, esResponse);
        }
    }

    /**
     * 获取请求头
     * @param username
     * @param password
     * @return
     */
    public static Map<String,String> getHeaders(String username, String password){
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Authorization", "Basic " + Base64.getUrlEncoder().encodeToString((username + ":" + password).getBytes()));
        return headers;
    }

    /**
     * 临时
     * @return
     */
    public static EsConfiguration getEsConfiguration(){
        String url = "http://localhost:9200/_sql?format=json";
        String username = "elastic";
        String password = "123456";
        EsConfiguration esConfiguration = new EsConfiguration();
        esConfiguration.setUrl(url);
        esConfiguration.setUsername(username);
        esConfiguration.setPassword(password);
        return esConfiguration;
    }

    /**
     * 获取请求体
     * @param esRequest
     * @return
     */
    public static JSONObject getRequestBody(EsRequest esRequest){
        JSONObject requestBody = new JSONObject();
        requestBody.put("query", esRequest.getSql());
        requestBody.put("fetch_size", esRequest.getFetchSize());
        return requestBody;
    }

    public static void test01() {
        // 指定URL
        String url = "http://localhost:9200/_sql?format=json";
        String username = "elastic";
        String password = "123456";

        // 存放请求头，可以存放多个请求头
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Authorization", "Basic " + Base64.getUrlEncoder().encodeToString((username + ":" + password).getBytes()));

        // 存放参数
        String sql = "SELECT * FROM test WHERE name like 'John Doe%' order by name";
        JSONObject requestBody = new JSONObject();
        requestBody.put("query", sql);
        String result = PostMethodUtils.sendPost(url, requestBody.toString(), username, password);
        System.out.println(result);
    }

    /**
     * TODO
     * 外部接口调用Post
     *
     * @author hejie
     * @date 2021/7/29 17:13
     * https://blog.csdn.net/zhaofuqiangmycomm/article/details/122436935
     */
    public static class PostMethodUtils {

        /**
         * 发送HttpClient请求
         *
         * @param requestUrl
         * @param params
         * @return
         */
        public static String sendPost(String requestUrl, String params, String username, String password) {
            InputStream inputStream = null;
            try {
                HttpClient httpClient = new HttpClient();
                PostMethod postMethod = new PostMethod(requestUrl);
                // 设置请求头  Content-Type
                postMethod.setRequestHeader("Content-Type", "application/json");
                //Base64加密方式认证方式下的basic auth HAIN460000：用户名    topicis123：密码
                postMethod.setRequestHeader("Authorization", "Basic " + Base64.getUrlEncoder().encodeToString((username + ":" + password).getBytes()));
                RequestEntity requestEntity = new StringRequestEntity(params, "application/json", "UTF-8");
                postMethod.setRequestEntity(requestEntity);
                httpClient.executeMethod(postMethod);// 执行请求
                inputStream = postMethod.getResponseBodyAsStream();// 获取返回的流
                BufferedReader br = null;
                StringBuffer buffer = new StringBuffer();
                // 将返回的输入流转换成字符串
                br = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("UTF-8")));
                String temp;
                while ((temp = br.readLine()) != null) {
                    buffer.append(temp);
                }
//                log.info("接口返回内容为:" + buffer);
                return buffer.toString();
            } catch (Exception e) {
//                log.info("请求异常" +e.getMessage());
                throw new RuntimeException(e.getMessage());
            } finally {
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

}

