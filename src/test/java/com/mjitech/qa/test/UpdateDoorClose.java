package com.mjitech.qa.test;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.testng.annotations.Test;

import com.mjitech.qa.service.BaseService;

import junit.framework.Assert;
import net.sf.json.JSONObject;

/**
 * 关门接口
 * @author gangwang
 * @date 2018-07-27
 * */
public class UpdateDoorClose {
	BaseService service = new BaseService();
	JSONObject json = JSONObject.fromObject("{}");
	//http://localhost:8080/maxbox/web/machine_pad/update_close_door.action
	@Test
	public void update_close_door() {
		String url = "http://preprod.mjitech.com/web/machine_api/update_door_close.action" ;
		String body = "{\"storeId\":\"12\",\"employeeId\":\"20\"}";
		try {
			JSONObject result = service.httpPostFlat(url, body);
			Assert.assertEquals("true",result.getString("is_succ"));
			System.out.println("更新关闭接口："+result);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
	}
}
