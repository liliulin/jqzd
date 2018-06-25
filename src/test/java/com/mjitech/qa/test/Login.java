package com.mjitech.qa.test;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.testng.annotations.Test;

import com.mjitech.qa.data.LoginDate;
import com.mjitech.qa.service.BaseService;

import junit.framework.Assert;
import net.sf.json.JSONObject;
/**
 * 登录接口
 * @author android
 * @date 2018-05-23
 * */
public class Login {
	BaseService service  =new BaseService();
	JSONObject json = JSONObject.fromObject("{}");
	@Test(dataProvider = "loginFenJian", dataProviderClass = LoginDate.class)
	public void login(String loginName,String password) {
		String url = "http://test.mjitech.com/web/wx_work_login_api/login.action" ;
		try {
			json.put("loginName",loginName );
			json.put("password", password);
			json = service.httppostReturnJson(url, service.postParameter(json));
			System.out.println("login result:"+json);
			JSONObject meta = json.getJSONObject("meta");
			if(meta!=null) {
				String code = meta.getString("code");
				Assert.assertEquals("200", code);
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
