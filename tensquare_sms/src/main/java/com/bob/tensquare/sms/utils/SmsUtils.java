package com.bob.tensquare.sms.utils;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component//该类交由spring容器管理
public class SmsUtils {


    @Autowired
    private Environment env;//获取配置文件application.yml所有的键值对

    public CommonResponse sendSms(String mobile,String template_code,String sign_name,String param) {

        //获取阿里云服务的通行证
        String accessKeyId =env.getProperty("aliyun.sms.accessKeyId");
        String accessKeySecret = env.getProperty("aliyun.sms.accessKeySecret");

        //设置通信证
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);


        IAcsClient client = new DefaultAcsClient(profile);
        CommonRequest request = new CommonRequest();

        request.setSysMethod(MethodType.POST);
        request.setSysDomain("dysmsapi.aliyuncs.com");
        request.setSysVersion("2017-05-25");
        request.setSysAction("SendSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        request.putQueryParameter("PhoneNumbers", mobile);//接受者电话
        request.putQueryParameter("SignName", sign_name);//短信签名
        request.putQueryParameter("TemplateCode", template_code);//短信模板
        request.putQueryParameter("TemplateParam", param);//发送给用户的验证码
        CommonResponse response=null;
        try {
            response = client.getCommonResponse(request);
            System.out.println(response.getData());

        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return response;
    }

}
