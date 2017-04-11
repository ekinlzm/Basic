package com.tinnotech.basic;

/**
 * Created by LZM on 2017/2/9.
 */

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSONObject;

import android.net.Uri;

public class ServiceClient {
    private String host;
    private int port;
    private String apiKey;
    private String apiSecret;
    private final static String SPECIAL_STRING = "~!@#$%^&*()_+:|\\=-,./?><;'][";

    public ServiceClient(String host, int port, String apiKey, String apiSecret) {
        this.host = host;
        this.port = port;
        this.apiKey = apiKey;
        this.apiSecret = apiSecret;
    }

    public JSONObject invoke(String path, Map<String, String> param) throws ServiceException {
        HttpEntity httpEntity = null;
        if (null != param && param.size() > 0) {
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            for (Entry<String, String> entry : param.entrySet()) {
                params.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
            try {
                httpEntity = new UrlEncodedFormEntity(params, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return execute(this.getUri(path, null), httpEntity);
    }

    private JSONObject execute(String uri, HttpEntity httpEntity) throws ServiceException {
        try {
            HttpPost httpPost = new HttpPost(uri);
            if (null != httpEntity) {
                httpPost.setEntity(httpEntity);
            }

            HttpClient httpClient = getHttpClient();
            HttpResponse httpResponse = httpClient.execute(httpPost);

            StatusLine statusLine = httpResponse.getStatusLine();
            if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
                String content = EntityUtils.toString(httpResponse.getEntity());
                JSONObject obj = JSONObject.parseObject(content);
                int code = obj.getIntValue("code");
                if (0 != code) {
                    String errmsg = obj.getString("errmsg");
                    throw new ServiceException(code, errmsg);
                }
                return obj;

            } else {
                throw new ServiceException(statusLine.getStatusCode(), statusLine.getReasonPhrase());
            }
        } catch (IOException ex) {
            throw new ServiceException(1000, "系统错误", ex);
        }
    }

    private HttpClient getHttpClient() {
        HttpClient httpClient = new DefaultHttpClient();
        return httpClient;
    }

    private String getUri(String path, Map<String, String> param) {
        String url = "http://" + this.host + ":" + this.port + path + "?" + this.getParamString(param);
        String uri = Uri.encode(url, SPECIAL_STRING);
        return uri;
    }

    private String getParamString(Map<String, String> param) {
        String curtime = "" + System.currentTimeMillis() / 1000;
        String paramString = "apikey=" + this.apiKey + "&timestamp=" + curtime + "&sign=" + getSign(curtime);
        if (null != param) {
            for (Entry<String, String> entry : param.entrySet()) {
                if (paramString.equalsIgnoreCase("")) {
                    paramString += entry.getKey() + "=" + entry.getValue();
                } else {
                    paramString += "&" + entry.getKey() + "=" + entry.getValue();
                }
            }
        }
        return paramString;
    }

    private String getSign(String curtime) {
        String md5string = this.apiKey + "," + this.apiSecret + "," + curtime;
        return TntUtil.toMD5Hex(md5string).toLowerCase();
    }

}
