package com.mjitech.qa.data;

import org.testng.annotations.DataProvider;

public class SetUserPasswordData {
	@DataProvider(name="setUserPassWord")
	public static Object[][] setUserPassWord() {
		return new Object[][] {{"fenjian","yier34wu6","yier34wu6"}};
	}
}
