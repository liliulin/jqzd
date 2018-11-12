package com.mjitech.qa.test;

import org.testng.log4testng.Logger;

public class Test {
	private static Logger logger = Logger.getLogger(Test.class);
	
	public static void main(String[] args) {
		 logger.debug("debug test");
	     logger.info("info test");
	     logger.error("error test....");
	}
}
