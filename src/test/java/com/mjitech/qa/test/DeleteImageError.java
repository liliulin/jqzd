package com.mjitech.qa.test;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.testng.annotations.Test;

import com.mjitech.qa.service.BaseService;

import net.sf.json.JSONObject;

/**
 * 删除图片接口
 * @author gangwang
 * @date 2018-07-30
 * */
public class DeleteImageError {
	BaseService service = new BaseService();
	JSONObject json = JSONObject.fromObject("{}");
	@Test
	public void delete_image_error() {
		String url = "http://preprod.mjitech.com/maxbox/web/delete_image_error.action" ;
		String body = "{\"storeId\":\"12\",\"employeeId\":\"20\"}";
		try {
			JSONObject result = service.httpPostFlat(url, body);
			System.out.println("删除图片："+result);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
