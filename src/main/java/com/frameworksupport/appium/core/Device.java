package com.frameworksupport.appium.core;



import java.util.Random;
import com.frameworksupport.util.general.CommonUtil;

public class Device {
  public static String getAndroidDeviceNameOnCloud() {
    return getDeviceName("CLOUD_ANDROID_MOBILE_DEVICES");
  }
  
  public static String getAndroidTabletNameOnCloud() {
    return getDeviceName("CLOUD_ANDROID_TABLET_DEVICES");
  }
  
  public static String getIphoneNameOnCloud() {
    return getDeviceName("CLOUD_IOS_MOBILE_DEVICES");
  }
  
  public static String getIpadNameOnCloud() {
    return getDeviceName("CLOUD_IOS_TABLET_DEVICES");
  }
  public static String getCloudMobileDeviceName() {
	  return getDeviceName("CLOUD_MOBILE_DEVICE_NAME");
  }
  
  public static String getSauceAndroidRealDevice() {
	    return getDeviceName("sauce_android_device");
	  }
	  
	  public static String getSauceiOSRealDevice() {
	    return getDeviceName("sauce_ios_device");
	  }
	  
	  public static String getSauceiOSSimulator() {
	    return getDeviceName("sauce_ios_simulator");
	  }
	  
	  public static String getSauceAndroidEmulator() {
	    return getDeviceName("sauce_android_emulator");
	  }
  
  private static String getDeviceName(String key) {
    String devices = CommonUtil.getMobileConfigProperty(key);
    String[] deviceArray = null;
    if (devices.contains(",")) {
      deviceArray = devices.split(",");
      Random random = new Random();
      return deviceArray[random.nextInt(deviceArray.length)].trim();
    } 
    return devices.trim();
  }
}
