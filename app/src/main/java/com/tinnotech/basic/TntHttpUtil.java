package com.tinnotech.basic;


import android.util.Log;

import java.util.HashMap;
import java.util.Map;
import com.alibaba.fastjson.JSONObject;
/**
 * Created by LZM on 2017/2/8.
 */

public class TntHttpUtil {
    public static final int RET_SUCCESS = 0;
    public static final int RET_ERROR_SERVER_INTER = 1000; //服务器内部错误
    public static final int RET_ERROR_PARAMETER_ABSENT = 1001; //缺少必要参数
    public static final int RET_ERROR_APIKEY_INVALID = 1002; //无效的apikey
    public static final int RET_ERROR_SIG_INVALID = 1003; //无效签名
    public static final int RET_ERROR_TOKEN_INVALID = 1004; //无效的token或 token已过期
    public static final int RET_ERROR_RESOURCE_NOT_EXIST = 1005; //访问的资源不存在
    public static final int RET_ERROR_PHONE_REGISTERED = 1100; //手机号已注册
    public static final int RET_ERROR_MEMBER_NOT_EXIST = 1101; //手机号对应的用户不存在或密码错误

    public static int MemberCheck(String phone)
    {
        int status = 0;
        try {
            ServiceClient client = new ServiceClient(TntConstants.SERVER_HOST, TntConstants.SERVER_PORT, TntConstants.API_KEY,
                    TntConstants.API_SECRET);
            Map<String, String> param = new HashMap<String, String>();
            param.put("phone", phone);

            JSONObject result = client.invoke(TntConstants.MEMBER_EXIST_URL, param);
            Log.i("MemberCheck", result.toJSONString());
            if (result.getInteger("errcode") == 0) {
                JSONObject data = result.getJSONObject("data");
                status = data.getInteger("status");
            }
        } catch (ServiceException e) {
            e.printStackTrace();
            status = -1;
        }

        return status;
    }

    public static int Register(String phone, String name, String pwd)
    {
        int ret = -1;
        try {
            ServiceClient client = new ServiceClient(TntConstants.SERVER_HOST, TntConstants.SERVER_PORT, TntConstants.API_KEY,
                    TntConstants.API_SECRET);
            Map<String, String> param = new HashMap<String, String>();
            param.put("phone", phone);
            param.put("password", pwd);
            param.put("name", name);

            JSONObject result = client.invoke(TntConstants.REGISTER_URL, param);
            Log.i("Register", result.toJSONString());
            ret = result.getInteger("errcode");
        } catch (ServiceException e) {
            e.printStackTrace();
        }

        return ret;
    }

    public static JSONObject Login(String phone, String pwd)
    {
        JSONObject result;
        try {
            ServiceClient client = new ServiceClient(TntConstants.SERVER_HOST, TntConstants.SERVER_PORT, TntConstants.API_KEY,
                    TntConstants.API_SECRET);
            Map<String, String> param = new HashMap<String, String>();
            param.put("phone", phone);
            param.put("password", pwd);

            result = client.invoke(TntConstants.LOGIN_URL, param);
            Log.i("Login", result.toJSONString());
            return result;
        } catch (ServiceException e) {
            e.printStackTrace();
            return null;
        }
    }
}
