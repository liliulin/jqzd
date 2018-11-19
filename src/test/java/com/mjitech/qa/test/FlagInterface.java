package com.mjitech.qa.test;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.junit.Assert;
import org.testng.annotations.Test;
import com.mjitech.qa.service.BaseService;

import net.sf.json.JSONObject;

/**
 * 平板接口逻辑
 * 
 * @author caoyue
 * @date 2018-11-12
 */
public class FlagInterface {
	BaseService service = new BaseService();
	JSONObject json = JSONObject.fromObject("{}");

	/**
	 * 统一获取门店信息
	 * 
	 * @param storeId
	 *            门店ID
	 * @author caoyue
	 */
	@Test
	public void getMachineData() {
		String url = "http://test.mjitech.com/web/pad_warehouse/getMachineData.action";
		String body = "{\"storeId\":\"13\"}";
		try {
			JSONObject result = service.httpPostFlat(url, body);
			String code = result.getJSONObject("meta").getString("code");
			String name = result.getJSONObject("data").getJSONObject("warehouse").getString("name");
			Assert.assertEquals("200", code);
			Assert.assertEquals("华贸商业街", name);
			System.out.println("获取订单信息：" + result);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 获取有权限的任务类型接口
	 * 
	 * @param employeeId
	 * @param storeId
	 *            门店ID
	 * @author caoyue
	 * @date 2018-11-12
	 */
	@Test
	public void getAlowedTaskAction() {
		String url = "http://test.mjitech.com/web/machine_pad/get_alowed_task.action";
		String body = "{\"employeeId\":\"1\",\"storeId\":\"13\"}";
		try {
			JSONObject result = service.httpPostFlat(url, body);
			String code = result.getJSONObject("meta").getString("code");
			Assert.assertEquals("200", code);
			System.out.println("获取有权限的任务类型接口：" + result);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
