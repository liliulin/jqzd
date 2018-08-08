package com.mjitech.qa.test;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.testng.annotations.Test;
import com.mjitech.qa.service.BaseService;
import net.sf.json.JSONObject;

/**
 * 故障检查任务之一-已知故障-删除图片接口
 * @author gangwang
 * @date 2018-07-30
 * */
public class DeleteImageError {
	BaseService service = new BaseService();
	JSONObject json = JSONObject.fromObject("{}");
	@Test
	public void delete_image_error() {
		String url = "http://preprod.mjitech.com/web/delete_image_error.action";
		String body = "{\"fileId\":\"3213\",\"errorId\":\"432\",\"type\":\"KN\"}";
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
