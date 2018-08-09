package com.mjitech.qa.test;
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
	
	@Test
	public void upload_image_list() {
		String url = "http://preprod.mjitech.com/web/upload_image_list.action" ;
		//{"meta":{"code":"200","message":"成功","success":true},"data":[3877]}
		String result = service.uploadImageList(url);  
		System.out.println("平板-上传图片信息接口："+result);
		JSONObject stringtoJson = JSONObject.fromObject(result);	
		String code = stringtoJson.getJSONObject("meta").getString("code");
		Assert.assertEquals("200", code);
		
	}
}
