package com.mjitech.qa.test;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.mjitech.qa.service.BaseService;

import net.sf.json.JSONObject;

/**
 * 故障列表接口
 * @author gangwang
 * @data  2018-07-27
 * */
public class ListFault {
	BaseService service = new BaseService();
	JSONObject json = JSONObject.fromObject("{}");
	@Test
	public void list_fault() {
		String url = "http://preprod.mjitech.com/web/machine_pad/list_fault.action" ;
		String body = "{\"storeId\":\"12\",\"employeeId\":\"20\"}";
		try {
			JSONObject result = service.httpPostFlat(url, body); 
			String code = result.getJSONObject("meta").getString("code");
			Assert.assertEquals("200", code);
			String data  = result.getString("data");
			Assert.assertNotNull(data);
			System.out.println("故障列表:"+result);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
