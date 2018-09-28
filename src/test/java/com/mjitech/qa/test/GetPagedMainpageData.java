package com.mjitech.qa.test;

import java.io.IOException;
import org.apache.http.client.ClientProtocolException;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.mjitech.qa.service.BaseService;
import net.sf.json.JSONObject;

/**
 * 微信端首页切换接口
 * @author gangwang
 * @date 2018-09-28
 * */
public class GetPagedMainpageData {
	BaseService service = new BaseService();
	@Test
	public void test_login_with_openid() {
		String url = "http://www.mjitech.com/web/buyer_api/test_login_with_openid.action" ;
		String body = "{\"openid\":\"o41Mgv5qXayH9P9C6IGYhN1Ujz3g\"}";
		try {
			long startTime = System.currentTimeMillis();
			JSONObject result = service.httpPostFlat(url, body);
			System.out.println("login result:"+result);
			long endTime = System.currentTimeMillis();
			long dd = endTime-startTime;	
			boolean flag = false ;
			if(dd <=300) {
				flag = true ;
			} else {
				flag = false ;
			}			
			Assert.assertEquals(flag, true);		
			//{"is_succ":true,"userinfo":{"image":"/tmp/2018-06-23/1529767666568.png","outDate":0,"isEmployee":1,"unionId":"o2bX9wYWM6OW2SGhVaBqR0H7As6Y","gender":0,"openId":"o41Mgv5qXayH9P9C6IGYhN1Ujz3g","displayName":"张智","roles":[],"isManagerAuth":0,"mobile":"139****1145","isSubscribe":1,"displayNamePinyin":"zhang zhi ","nickname":"张智","userType":999,"id":1,"email":"zhangzhi@mjitech.com","username":"zhangzhi@mjitech.com"}}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	@Test(dependsOnMethods = "test_login_with_openid")
	public void get_paged_mainpage_data() {
		String url = "http://www.mjitech.com/web/buyer_api/get_paged_mainpage_data.action" ;
		String body = "{\"storeId\":\"13\",\"category\":\"4\",\"subCategory\":\"22\",\"from\":\"0\",\"to\":\"10\"}" ;
		try {
			long startTime = System.currentTimeMillis();
			JSONObject	result = service.httpPostFlat(url, body);
			long endTime = System.currentTimeMillis();
			System.out.println("get_paged_mainpage_data result:"+result);
			long dd = endTime-startTime;	
			System.out.println("首页分类接口请求时间差："+dd);
			boolean flag = false ;
			if(dd <=300) {
				flag = true ;
			} else {
				flag = false ;
			}			
			Assert.assertEquals(flag, true);								
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
		
}
