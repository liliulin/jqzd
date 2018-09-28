package com.mjitech.qa.test;
import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.testng.annotations.Test;
import com.mjitech.qa.service.BaseService;
import junit.framework.Assert;
import net.sf.json.JSONObject;

/**
 * 批量上传图片返回图片idList
 * @author gangwang
 * @date 2018-08-06
 * */
public class UploadImageList {
	BaseService service = new BaseService();
	JSONObject json = JSONObject.fromObject("{}");
	String data = "" ;
	
	/**扫描二维码获取故障信息接口*/
//	@Test
	public void two_bar_codes_detail() {		
		String url = "http://preprod.mjitech.com/web/machine_pad/two_bar_codes_detail/KN/432" ;
		System.out.println("url="+url);
		try {			
			String result = service.httppost(url);
			//扫描二维码获取故障信息接口:{"meta":{"code":"200","message":"成功","success":true},"data":{"errorCode":null,"message":"1:11 回零故障,伺服电机到位超时","type":"KN","fileModelVoList":[]}}
			System.out.println("扫描二维码获取故障信息接口:"+result);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
//	@Test
	public void upload_image_list() {
		String url = "http://preprod.mjitech.com/web/upload_image_list.action" ;
		//{"meta":{"code":"200","message":"成功","success":true},"data":[3877]}
		String result = service.uploadImageList(url);  
		System.out.println("平板-上传图片信息接口："+result);
		JSONObject stringtoJson = JSONObject.fromObject(result);	
		String code = stringtoJson.getJSONObject("meta").getString("code");
		Assert.assertEquals("200", code);		
		if(code.equals("200")) {
			data = stringtoJson.getString("data");
		}		
	}
	///machine_pad/fault/{type}/{id}
	/**
	 * 上传图片之后调用这个接口保存图片到
	 * */
	//@Test(dependsOnMethods="upload_image_list")
	@Test
	public void fault() {
		String url = "http://preprod.mjitech.com/web/machine_pad/fault/KN/432" ;
		json.put("files", "3967");
	//	String body = "{\"files\":\""+data+"\"}";
		try {
			String result = service.httppost(url, service.postParameter(json));
			
			//JSONObject result = service.httpPostFlat(url, body);	
			System.out.println("保存图片："+result);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
