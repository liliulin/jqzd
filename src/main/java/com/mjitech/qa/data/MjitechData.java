//package com.mjitech.qa.data;
//
//import org.testng.annotations.DataProvider;
//
///**
// * 数据层：数据与数据之间用“，”号分割，最后一个字段为接口状态字段，例如“OK”或“ERROR”
// * 
// * @author android
// * @date 2018-05-17
// */
//public  class MjitechData {
//	// 无指定数据名称，默认用方法名
//	@DataProvider
//	public static Object[][] NoNameMethod() {
//		return new Object[][] {};
//	}
//
//	/**
//	 * 按照名字进行检索数据
//	 */
//	@DataProvider(name = "b_regist")
//	public static Object[][] bRegistDataProvider() {
//		return new Object[][] { { "PPS011509000000005", "", "ZC02050002" },
//				{ "6226090106597152", "007", "王五", "530101198512140018", 7385952L, "招商银行", "11", "11", "招商银行北京分行",
//						"03080000", "308100005027", "13911708375", "成功" }, };
//	}
//}
