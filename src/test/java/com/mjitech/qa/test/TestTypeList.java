package com.mjitech.qa.test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.URI;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClients;
import org.testng.annotations.Test;

import com.mjitech.qa.data.LoginDate;
import com.mjitech.qa.service.BaseService;

import junit.framework.Assert;
import net.sf.json.JSONObject;

/**
 * 任务列表(分拣，打包,配送各种)
 * 
 * @author android
 * @date 2018-05-30
 */
public class TestTypeList {
	BaseService service = new BaseService();
	JSONObject json = JSONObject.fromObject("{}");
	String token = "";
	String employeeid = "";

	@Test
	public void login() {
		String url = "http://localhost:8080/maxbox/web/wx_work_login_api/login.action";
		try {
			json.put("loginName", "fenjiantest");
			json.put("password", "123456");
			json = service.httppostReturnJson(url, service.postParameter(json));
			JSONObject data = json.getJSONObject("data");
			JSONObject employee = data.getJSONObject("employee");
			employeeid = employee.getString("id");
			token = data.getString("token");

		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test(dependsOnMethods = "login")
	public void type_list() {
		String url = "http://localhost:8080/maxbox/web/wx_work_api/type_list.action";
		String body = "{\"type\":\"ST\"}";
		try {
			JSONObject result = service.httppostHeaderReturnJson(url, employeeid, token,body);
			JSONObject meta = result.getJSONObject("meta");
			if (meta != null) {
				String code = meta.getString("code");
				Assert.assertEquals("200", code);
				Assert.assertEquals("成功", meta.getString("message"));
				Assert.assertEquals("true", meta.getString("success"));
			}
			System.out.println("reuslt=" + result);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
