package com.mjitech.qa.data;

import org.testng.annotations.DataProvider;

import com.mjitech.qa.data.MjitechData;

public class LoginDate extends MjitechData {
	@DataProvider(name="loginFenJian")
	public static Object[][] loginDate() {
		return new Object[][]{
			{"fenjian","yier34wu6"},
			{"dabao","yier34wu6"},
			{"peisong","yier34wu6"},
			{"jiqikuguan","yier34wu6"}};
	}
}
