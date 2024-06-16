package com.frameworksupport.influxdb.core;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class InfluxDBListener implements ITestListener {

	InfluxDBUtil influxDBUtil = new InfluxDBUtil();

	public void onTestSuccess(ITestResult iTestResult) {
		influxDBUtil.postTestMethodStatus(iTestResult, "PASS");
	}

	public void onTestFailure(ITestResult iTestResult) {
		influxDBUtil.postTestMethodStatus(iTestResult, "FAIL");
	}

	public void onTestSkipped(ITestResult iTestResult) {
		influxDBUtil.postTestMethodStatus(iTestResult, "SKIPPED");
	}

	public void onFinish(ITestContext iTestContext) {
		influxDBUtil.postTestClassStatus(iTestContext);
		influxDBUtil.InfluxDBConnectionClose();
	}

}
