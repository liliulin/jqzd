package com.mjitech.qa.test;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.testng.annotations.Test;

import com.mjitech.qa.data.SetUserPasswordData;
import com.mjitech.qa.service.BaseService;

import junit.framework.Assert;
import net.sf.json.JSONObject;
/**
 * 设置初始密码
 * @author android
 * @date 2018-05-24
 * */
public class SetUserPasswrod  {
	BaseService service = new BaseService();
	JSONObject json = JSONObject.fromObject("{}");
	@Test(dataProvider="setUserPassWord",dataProviderClass=SetUserPasswordData.class)
	public void setUserPassword(String loginName,String password,String confirPassword) {
		String url = "http://test.mjitech.com/web/wx_work_login_api/set_user_passwrod.action" ;
		System.out.println("test......");
		json.put("loginName", loginName);
		json.put("password",password);
		json.put("confirPassword",confirPassword) ;
		try {
			json = service.httppostReturnJson(url, service.postParameter(json));
			JSONObject meta = json.getJSONObject("meta");
			if(meta!=null) {
				String code = meta.getString("code");
				Assert.assertEquals("200", code);
				String message = meta.getString("message");
				Assert.assertEquals("密码必须有一个大写字母", message);
			}
			System.out.println("result="+json);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
