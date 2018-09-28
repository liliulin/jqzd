package com.mjitech.qa.test;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.http.client.ClientProtocolException;
import org.junit.Assert;
import org.testng.annotations.Test;

import com.mjitech.qa.service.BaseService;

import net.sf.json.JSONObject;

/**
 * 首页-统一获取门店信息
 * @author gangwang
 * @date 2018-07-25
 * */
public class GetMachineData27 {
	BaseService service = new BaseService();
	JSONObject json = JSONObject.fromObject("{}");
	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	/**
	 * 华贸商业街
	 * */
	@Test
	public void getMachineData13() {		
		String url = "http://www.mjitech.com/web/machine_api/get_mainpage_data.action" ;
		json.put("storeId", "13");
		try {
			String startTime = df.format(new Date());
			System.out.println("华贸商业街获取首页接口start time："+startTime);
			JSONObject result = service.httppostCartReturnJson27(url, service.postParameter(json));
			String endTime = df.format(new Date());
			System.out.println("华贸商业街获取首页接口 end time:"+df.format(new Date()));
	//		String dd = endTime-startTime;
		//	System.out.println("华贸商业街获取首页接口响应时间："+(endTime-startTime));
		//	System.out.println("华贸商业街-获取首页信息接口："+result);
			String is_succ = result.getString("is_succ");
			Assert.assertEquals("true", is_succ);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 后现代城
	 * */
//	@Test
	public void getMachineData22() {		
		String url = "http://www.mjitech.com/web/machine_api/get_mainpage_data.action" ;
		json.put("storeId", "22");
		try {
			JSONObject result = service.httppostCartReturnJson27(url, service.postParameter(json));
			System.out.println("获取首页信息接口："+result);
			String is_succ = result.getString("is_succ");
			Assert.assertEquals("true", is_succ);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 华贸写字楼店
	 * */
//	@Test
	public void getMachineData20() {		
		String url = "http://www.mjitech.com/web/machine_api/get_mainpage_data.action" ;
		json.put("storeId", "20");
		try {
			JSONObject result = service.httppostCartReturnJson27(url, service.postParameter(json));
			System.out.println("华贸写字楼店-获取首页信息接口："+result);
			String is_succ = result.getString("is_succ");
			Assert.assertEquals("true", is_succ);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
