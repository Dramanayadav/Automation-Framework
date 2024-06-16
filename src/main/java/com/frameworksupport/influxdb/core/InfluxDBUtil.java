package com.frameworksupport.influxdb.core;

import java.time.Instant;
import org.testng.ITestContext;
import org.testng.ITestResult;
import com.frameworksupport.util.general.CommonUtil;
import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.InfluxDBClientFactory;
import com.influxdb.client.WriteApiBlocking;
import com.influxdb.client.domain.WritePrecision;
import com.influxdb.client.write.Point;
import com.influxdb.exceptions.InfluxException;

public class InfluxDBUtil {

	private String token;
	private String bucket;
	private String org;
	private String url;
	InfluxDBClient influxDBClient;

	// Connecting to influxDB
	public void buildConnection() {
		token = CommonUtil.getConfigProperty("grafana_token");
		bucket = CommonUtil.getConfigProperty("grafana_bucketName");
		org = CommonUtil.getConfigProperty("grafana_organizationName");
		url = CommonUtil.getConfigProperty("grafana_url");

		setToken(token);
		setBucket(bucket);
		setOrg(org);
		setUrl(url);
		influxDBClient = InfluxDBClientFactory.create(url, token.toCharArray(), org, bucket);
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getBucket() {
		return bucket;
	}

	public void setBucket(String bucket) {
		this.bucket = bucket;
	}

	public String getOrg() {
		return org;
	}

	public void setOrg(String org) {
		this.org = org;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void postTestMethodStatus(ITestResult iTestResult, String status) {
		try {
			this.buildConnection();
			WriteApiBlocking writeApi = influxDBClient.getWriteApiBlocking();
			Point point;
			if (iTestResult.getStatus() == 1) {
				point = Point.measurement("Testmethod").addTag("Testclass", iTestResult.getTestClass().getName())
						.addTag("Name", iTestResult.getName()).addTag("Result", status)
						.addField("Duration", (iTestResult.getEndMillis() - iTestResult.getStartMillis()))
						.time(Instant.now(), WritePrecision.MS);
			} else {
				point = Point.measurement("Testmethod").addTag("Testclass", iTestResult.getTestClass().getName())
						.addTag("Name", iTestResult.getName()).addTag("Result", status)
						.addTag("Error", iTestResult.getThrowable().toString())
						.addField("Duration", (iTestResult.getEndMillis() - iTestResult.getStartMillis()))
						.time(Instant.now(), WritePrecision.MS);
			}
			writeApi.writePoint(point);
		} catch (InfluxException e) {
			System.out.println("Exception!!" + e.getMessage());
		}

	}

	public void postTestClassStatus(ITestContext iTestContext) {
		try {
			this.buildConnection();
			WriteApiBlocking writeApi = influxDBClient.getWriteApiBlocking();
			Point point = Point.measurement("TestClass")
					.addTag("Name", iTestContext.getAllTestMethods()[0].getTestClass().getName())
					.addField("Duration", (iTestContext.getEndDate().getTime() - iTestContext.getStartDate().getTime()))
					.time(Instant.now(), WritePrecision.MS);
			writeApi.writePoint(point);
		} catch (InfluxException e) {
			System.out.println("Exception!!" + e.getMessage());
		}

	}

	public void InfluxDBConnectionClose() {
		influxDBClient.close();
	}

}
