package com.shizy.utils.http;

import com.alibaba.fastjson.JSONObject;
import com.shizy.utils.format.FormatUtil;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.InputStreamBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.*;
import java.util.Map.Entry;

/**
 * 网络请求类
 *
 * @author zhouqixin
 */
public class HttpUtil {
    /**
     * 创建 HttpClient
     *
     * @param ssl true：创建HTTPS，false：创建HTTP
     * @return
     */
    private static CloseableHttpClient buildHttpClient(boolean ssl) {
        CloseableHttpClient httpClient = null;

        if (ssl) {
            httpClient = buildHttpsClient();
        } else {
            httpClient = buildHttpClient();
        }

        return httpClient;
    }

    private static CloseableHttpClient buildHttpClient() {
        return HttpClients.createDefault();
    }

    private static CloseableHttpClient buildHttpsClient() {
        // 未实现
        return null;
    }

    /**
     * 解析Http Response响应头
     *
     * @param response
     * @return
     */
    private static Map<String, String> parseHeader(HttpResponse response) {
        Header[] headers = response.getAllHeaders();

        if (headers != null) {
            Map<String, String> map = new HashMap<String, String>(headers.length);

            for (Header header : headers) {
                map.put(header.getName(), header.getValue());
            }

            // 不包括Cookie
            map.remove("Set-Cookie");

            return map;
        }

        return null;
    }

    /**
     * 解析Http Response响应Cookie
     *
     * @param response
     * @return
     */
    private static Map<String, String> parseCookie(HttpResponse response) {
        Header[] cookies = response.getHeaders("Set-Cookie");

        if (cookies != null) {
            Map<String, String> map = new HashMap<String, String>(cookies.length);

            for (Header header : cookies) {
                String cookie = header.getValue().substring(0, header.getValue().indexOf(";"));
                String[] kv = cookie.split("=");

                if (kv.length == 2) {
                    map.put(kv[0].trim(), kv[1].trim());
                } else {
                    map.put(kv[0].trim(), "");
                }
            }

            return map;
        }

        return null;
    }

    /**
     * 发起Http请求
     *
     * @param request
     * @param ssl        是否使用SSL
     * @param respHeader 是否需要解析响应头
     * @param respCookie 是否需要解析响应Cookie
     * @return
     * @throws Exception
     */
    private static Map<String, Object> doRequest(HttpUriRequest request, boolean ssl, boolean respHeader, boolean respCookie, String respEncode) throws Exception {
        return doRequest(request, ssl, respHeader, respCookie, -1, respEncode);
    }

    /**
     * 发起Http请求
     *
     * @param request
     * @param ssl            是否使用SSL
     * @param respHeader     是否需要解析响应头
     * @param respCookie     是否需要解析响应Cookie
     * @param connectionTime -1为取默认超时时间
     * @return
     * @throws Exception
     */
    private static Map<String, Object> doRequest(HttpUriRequest request, boolean ssl, boolean respHeader, boolean respCookie, int connectionTime, String respEncode) throws Exception {
        CloseableHttpClient httpClient;
        if (-1 != connectionTime) {
            /*超时时间设置*/
            RequestConfig defaultRequestConfig = RequestConfig.custom()
                    .setConnectionRequestTimeout(connectionTime)//从连接池等待获取连接的时间
                    .setConnectTimeout(connectionTime)//连接超时时间
                    .setSocketTimeout(connectionTime * 5)//获取数据的超时时间，这个给予多一点时间
                    .build();
            httpClient = HttpClients.custom()
                    .setDefaultRequestConfig(defaultRequestConfig)
                    .build();
        } else {
            /*取默认时间设置*/
            httpClient = buildHttpClient(ssl);
        }
        CloseableHttpResponse response = null;

        try {
            response = httpClient.execute(request);

            if (response.getStatusLine().getStatusCode() == 200) {
                Map<String, Object> map = new HashMap<String, Object>();

                if (respHeader) {
                    map.put("header", parseHeader(response));
                }

                if (respCookie) {
                    map.put("cookie", parseCookie(response));
                }

                map.put("content", EntityUtils.toString(response.getEntity(), respEncode));

                return map;
            }
        } finally {
            if (response != null) response.close();
            if (httpClient != null) httpClient.close();
        }

        return null;
    }

    /**
     * 提交POST表单
     *
     * @param url        接口地址
     * @param form       表单参数
     * @param headers    请求头
     * @param respHeader 是否需要解析响应头
     * @param respCookie 是否需要解析响应Cookie
     * @return
     */
    public static Map<String, Object> postHttpForm(String url, Map<String, String> form, Map<String, String> headers, boolean respHeader, boolean respCookie) throws Exception {

        HttpPost httpPost = new HttpPost(url);

        if (form != null && !form.isEmpty()) {
            List<NameValuePair> params = new ArrayList<NameValuePair>();

            for (Entry<String, String> entry : form.entrySet()) {
                params.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }

            httpPost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
        }

        if (headers != null && !headers.isEmpty()) {
            for (Entry<String, String> entry : headers.entrySet()) {
                httpPost.addHeader(entry.getKey(), entry.getValue());
            }
        }

        return doRequest(httpPost, false, respHeader, respCookie, "UTF-8");

    }

    /**
     * 提交POST表单
     *
     * @param url     接口地址
     * @param form    表单参数
     * @param headers 请求头
     * @return
     */
    public static String postHttpForm(String url, Map<String, String> form, Map<String, String> headers) throws Exception {
        Map<String, Object> response = postHttpForm(url, form, headers, false, false);

        if (response != null) {
            return String.valueOf(response.get("content"));
        }

        return null;
    }


    /**
     * 提交POST表单(包含文件类型)
     *
     * @param url        接口地址
     * @param param      表单字符串参数
     * @param files      表单文件类型参数
     * @param headers    请求头
     * @param respHeader 是否需要解析响应头
     * @param respCookie 是否需要解析响应Cookie
     * @return
     */
    public static String postHttpMultiForm(String url,
                                           Map<String, String> param, Map<String, MultipartFile> files, Map<String, String> headers, boolean respHeader, boolean respCookie, String reqEncode) {
        try {
            HttpPost httpPost = new HttpPost(url);
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            if (param != null && !param.isEmpty()) {
                for (Entry<String, String> entry : param.entrySet()) {
                    builder.addTextBody(entry.getKey(), entry.getValue(), ContentType.TEXT_PLAIN.withCharset(reqEncode));
                }

            }
            if (files != null && files.size() > 0) {
                Set<Entry<String, MultipartFile>> entries = files.entrySet();
                for (Entry<String, MultipartFile> entry : entries) {

                    if (entry.getValue().getContentType() != null) {
                        builder.addPart(entry.getKey(),
                                new InputStreamBody(entry.getValue().getInputStream(),
                                        ContentType.create(entry.getValue().getContentType()),
                                        entry.getValue().getOriginalFilename()));
                    } else {
                        builder.addBinaryBody(
                                entry.getKey(),
                                entry.getValue().getInputStream(),
                                ContentType.APPLICATION_OCTET_STREAM,
                                entry.getValue().getName()
                        );
                    }
                }
            }

            if (headers != null && !headers.isEmpty()) {
                for (Entry<String, String> entry : headers.entrySet()) {
                    httpPost.addHeader(entry.getKey(), entry.getValue());
                }
            }
            httpPost.setEntity(builder.build());
            Map<String, Object> response = doRequest(httpPost, false, respHeader, respCookie, reqEncode);
            if (response != null) {
                return String.valueOf(response.get("content"));
            }

            return null;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 提交POST Body
     *
     * @param url        接口地址
     * @param body       请求体
     * @param headers    请求头
     * @param respHeader
     * @param respCookie
     * @return
     */
    public static Map<String, Object> postHttpBody(String url, String body, Map<String, String> headers, boolean respHeader, boolean respCookie) throws Exception {

        HttpPost httpPost = new HttpPost(url);

        if (body != null) {
            httpPost.setEntity(new StringEntity(body, "UTF-8"));
        }

        if (headers != null && !headers.isEmpty()) {
            for (Entry<String, String> entry : headers.entrySet()) {
                httpPost.addHeader(entry.getKey(), entry.getValue());
            }
        }

        return doRequest(httpPost, false, respHeader, respCookie, "UTF-8");

    }

    public static Map<String, Object> postHttpBody(String url, String body, String bodyEconde, Map<String, String> headers, boolean respHeader, boolean respCookie) throws Exception {

        HttpPost httpPost = new HttpPost(url);

        if (body != null) {
            httpPost.setEntity(new StringEntity(body, bodyEconde));
        }

        if (headers != null && !headers.isEmpty()) {
            for (Entry<String, String> entry : headers.entrySet()) {
                httpPost.addHeader(entry.getKey(), entry.getValue());
            }
        }

        return doRequest(httpPost, false, respHeader, respCookie, bodyEconde);

    }

    public static String postHttpBody(String url, String body, String bodyEncode, Map<String, String> headers) throws Exception {
        Map<String, Object> response = postHttpBody(url, body, bodyEncode, headers, false, false);

        if (response != null) {
            return String.valueOf(response.get("content"));
        }

        return null;
    }

    /**
     * 提交POST Body
     *
     * @param url     接口地址
     * @param body    请求体
     * @param headers 请求头
     * @return
     */
    public static String postHttpBody(String url, String body, Map<String, String> headers) throws Exception {
        Map<String, Object> response = postHttpBody(url, body, headers, false, false);

        if (response != null) {
            return String.valueOf(response.get("content"));
        }

        return null;
    }

    /**
     * 提交GET请求
     *
     * @param url     接口地址
     * @param params  查询参数
     * @param headers 请求头
     * @return
     */
    public static String getHttp(String url, Map<String, String> params, Map<String, String> headers) throws Exception {
        Map<String, Object> response = getHttp(url, params, headers, false, false);

        if (response != null) {
            return String.valueOf(response.get("content"));
        }

        return null;
    }


    /**
     * 提交GET请求
     *
     * @param url        接口地址
     * @param params     查询参数
     * @param headers    请求头
     * @param respHeader
     * @param respCookie
     * @return
     */
    public static Map<String, Object> getHttp(String url, Map<String, String> params, Map<String, String> headers, boolean respHeader, boolean respCookie) throws Exception {
        return getHttp(url, params, headers, respHeader, respCookie, -1);
    }


    /**
     * 提交GET请求，并指定连接超时时间
     *
     * @param url     接口地址
     * @param params  查询参数
     * @param headers 请求头
     * @return
     */
    public static Map<String, Object> getHttp(String url, Map<String, String> params, Map<String, String> headers, int connectionTime) throws Exception {
        return getHttp(url, params, headers, false, false, connectionTime);
    }


    /**
     * 提交GET请求
     *
     * @param url            接口地址
     * @param params         查询参数
     * @param headers        请求头
     * @param respHeader
     * @param respCookie
     * @param connectionTime -1为取默认超时时间
     * @return
     */
    public static Map<String, Object> getHttp(String url, Map<String, String> params, Map<String, String> headers, boolean respHeader, boolean respCookie, int connectionTime) throws Exception {

        StringBuilder uri = new StringBuilder(url);

        if (params != null && !params.isEmpty()) {
            uri.append("?");

            for (Entry<String, String> entry : params.entrySet()) {
                uri.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
                uri.append("=");

                if (entry.getValue() != null) {
                    uri.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
                }

                uri.append("&");
            }
        }

        HttpGet httpGet = new HttpGet(uri.toString());

        if (headers != null && !headers.isEmpty()) {
            for (Entry<String, String> entry : headers.entrySet()) {
                httpGet.addHeader(entry.getKey(), entry.getValue());
            }
        }

        return doRequest(httpGet, false, respHeader, respCookie, connectionTime, "UTF-8");


    }

    public static Map<String, Object> getHttpBody(String url, String body, Map<String, String> params, Map<String, String> headers, boolean respHeader, boolean respCookie, int connectionTime) throws Exception {
        StringBuilder uri = new StringBuilder(url);

        if (params != null && !params.isEmpty()) {
            uri.append("?");

            for (Entry<String, String> entry : params.entrySet()) {
                uri.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
                uri.append("=");

                if (entry.getValue() != null) {
                    uri.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
                }

                uri.append("&");
            }
        }

        HttpGetWithEntity httpGetWithEntity = new HttpGetWithEntity(uri.toString());

        if (body != null) {
            httpGetWithEntity.setEntity(new StringEntity(body, ContentType.APPLICATION_JSON));
        }

        if (headers != null && !headers.isEmpty()) {
            for (Entry<String, String> entry : headers.entrySet()) {
                httpGetWithEntity.addHeader(entry.getKey(), entry.getValue());
            }
        }
        return doRequest(httpGetWithEntity, false, respHeader, respCookie, connectionTime, "UTF-8");
    }

    public static Map<String, Object> getHttpBody(String url, String body, Map<String, String> params, Map<String, String> headers, boolean respHeader, boolean respCookie, int connectionTime, String respEncode) throws Exception {
        StringBuilder uri = new StringBuilder(url);

        if (params != null && !params.isEmpty()) {
            uri.append("?");

            for (Entry<String, String> entry : params.entrySet()) {
                uri.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
                uri.append("=");

                if (entry.getValue() != null) {
                    uri.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
                }

                uri.append("&");
            }
        }

        HttpGetWithEntity httpGetWithEntity = new HttpGetWithEntity(uri.toString());

        if (body != null) {
            httpGetWithEntity.setEntity(new StringEntity(body, ContentType.APPLICATION_JSON));
        }

        if (headers != null && !headers.isEmpty()) {
            for (Entry<String, String> entry : headers.entrySet()) {
                httpGetWithEntity.addHeader(entry.getKey(), entry.getValue());
            }
        }
        return doRequest(httpGetWithEntity, false, respHeader, respCookie, connectionTime, respEncode);
    }


    /**
     * get 与 requestBody的组合
     *
     * @param url
     * @param body
     * @param params
     * @param headers
     * @return
     * @throws Exception
     */
    public static Map<String, Object> getHttpBody(String url, String body, Map<String, String> params, Map<String, String> headers) throws Exception {
        return getHttpBody(url, body, params, headers, false, false, -1);
    }

    /**
     * get 与 requestBody的组合
     *
     * @param url
     * @param body
     * @param params
     * @param headers
     * @return
     * @throws Exception
     */
    public static Map<String, Object> getHttpBody(String url, String body, Map<String, String> params, Map<String, String> headers, String respEncode) throws Exception {
        return getHttpBody(url, body, params, headers, false, false, -1, respEncode);
    }

    /**
     * 获得request的请求头信息string，用于展示
     *
     * @param request
     * @return
     * @throws IOException
     */
    public static String getHeadersInfo(HttpServletRequest request) throws IOException {

        Map<String, String> headerMap = new HashMap<String, String>();
        Enumeration headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = (String) headerNames.nextElement();
            String value = request.getHeader(key);
            headerMap.put(key, value);
        }

        Map<String, String> paramMap = new HashMap<String, String>();
        Enumeration paramNames = request.getParameterNames();
        while (paramNames.hasMoreElements()) {
            String key = (String) paramNames.nextElement();
            String value = request.getParameter(key);
            paramMap.put(key, value);
        }

        Map<String, Object> jsonMap = new HashMap<String, Object>();
        jsonMap.put("headers", headerMap);
        jsonMap.put("RequestURI", request.getRequestURI());
        jsonMap.put("RequestMethod", request.getMethod());
        jsonMap.put("QueryString", request.getQueryString());
        jsonMap.put("Parameters", paramMap);
        //jsonMap.put("InputStreamData", StreamUtil.read(request.getInputStream()));//这个流只能读一次

        return "\r\n" + FormatUtil.formatJson(JSONObject.toJSON(jsonMap).toString());
    }

}
