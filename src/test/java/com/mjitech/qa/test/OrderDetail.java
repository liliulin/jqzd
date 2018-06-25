package com.mjitech.qa.test;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.testng.annotations.Test;

import com.mjitech.qa.service.BaseService;

import net.sf.json.JSONObject;

public class OrderDetail {
	String url = "http://www.mjitech.com/web/buyer_api/order_detail_new" ;
	BaseService service = new BaseService() ;
	JSONObject json = JSONObject.fromObject("{}");
	@Test
	public void testOrder() {
		json.put("orderNumber", "S4094204721");
		try {
			String result = service.httppost(url, service.postParameter(json));
			System.out.println("result:"+result);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
