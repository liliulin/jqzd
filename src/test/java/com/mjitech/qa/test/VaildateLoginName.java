package com.mjitech.qa.test;

import java.io.IOException;
import org.apache.http.client.ClientProtocolException;
import org.testng.annotations.Test;

import com.mjitech.qa.data.VaildateLoginNameData;
import com.mjitech.qa.service.BaseService;
import com.mjitech.qa.service.HttpMethod;

import junit.framework.Assert;
import net.sf.json.JSONObject;

/**
 * 验证账户是否存在接口
 * 
 * @author android
 * @date 2018-05-23
 */
public class VaildateLoginName {
	JSONObject json = JSONObject.fromObject("{}");
	HttpMethod httpMethod = new HttpMethod();
	BaseService service = new BaseService();

	@Test(dataProvider = "vaildateLoginName", dataProviderClass = VaildateLoginNameData.class)
	public void VaildateLoginNameByDabao(String loginName) {
		String url = "http://test.mjitech.com/web/wx_work_login_api/vaildate_login_name.action";
		try {
			json.put("loginName", loginName);
			json = service.httppostReturnJson(url, service.postParameter(json));
			System.out.println("验证不同角色账户是否存在接口：" + json);
			JSONObject meta = json.getJSONObject("meta");
			if (meta != null) {
				String code = meta.getString("code");
				Assert.assertEquals("200", code);
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
