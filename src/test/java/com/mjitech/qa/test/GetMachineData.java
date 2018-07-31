package com.mjitech.qa.test;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.junit.Assert;
import org.testng.annotations.Test;

import com.mjitech.qa.service.BaseService;

import net.sf.json.JSONObject;

/**
 * 统一获取门店信息
 * @author gangwang
 * @date 2018-07-25
 * */
public class GetMachineData {
	BaseService service = new BaseService();
	JSONObject json = JSONObject.fromObject("{}");
	@Test
	public void getMachineData() {
		String url = "http://preprod.mjitech.com/web/pad_warehouse/getMachineData.action" ;
		String body = "{\"storeId\":\"12\"}";
		try {
			JSONObject result = service.httpPostFlat(url, body);
			String code = result.getJSONObject("meta").getString("code");
			String name = result.getJSONObject("data").getJSONObject("warehouse").getString("name");
			Assert.assertEquals("200", result.getJSONObject("meta").getString("code"));
			Assert.assertEquals("华贸测试机", name);
			System.out.println("获取订单信息："+result);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
