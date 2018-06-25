package com.mjitech.qa.data;

import org.testng.annotations.DataProvider;

/**
 * 验证用户是否存在接口
 * @author android
 * @date 2018-05-23
 * */
public class VaildateLoginNameData {
	@DataProvider(name="vaildateLoginName")
	public Object[][] vaildateLoginName(){
		return new Object[][] {
			{"fenjian"},
			{"dabao"},
			{"peisong"},
			{"jiqikuguan"}};
	}
}
