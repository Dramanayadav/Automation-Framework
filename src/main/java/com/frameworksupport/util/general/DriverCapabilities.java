package com.frameworksupport.util.general;

import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.ios.options.XCUITestOptions;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.github.bonigarcia.wdm.config.OperatingSystem;
import java.io.File;
import java.time.Duration;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.safari.SafariOptions;
import org.testng.Assert;
import com.frameworksupport.appium.core.Device;
import com.frameworksupport.setup.BaseActionDriver;
import com.frameworksupport.util.enums.Browser;

public class DriverCapabilities {
  private CommonUtil commonUtil;
  
  private BaseActionDriver actionDriver;
  
  public DriverCapabilities(BaseActionDriver actionDriver, CommonUtil commonUtil) {
    this.actionDriver = actionDriver;
    this.commonUtil = commonUtil;
  }
  
  public Capabilities androidNativeOptions() {
    this.commonUtil.log("Setting capabilities for Android Android Native App....");
    String hubUrl = CommonUtil.getConfigProperty("grid_hub_url");
    String deviceName = null;
    String platformVersion = null;
    String appiumVersion = null;
    if (Boolean.parseBoolean(CommonUtil.getMobileConfigProperty("run_mobile_test_on_saucelab"))) {
      if (hubUrl.contains("saucelabs")) {
        if (CommonUtil.getMobileConfigProperty("device_type").equalsIgnoreCase("simulator")) {
          String[] arr = CommonUtil.getMobileConfigProperty("sauce_android_emulator")
            .split("\\|");
          deviceName = arr[0];
          platformVersion = arr[1];
          appiumVersion = arr[2];
        } else {
          deviceName = Device.getSauceAndroidRealDevice();
        } 
        this.commonUtil.log("Hub Url: " + hubUrl);
        this.commonUtil.log("Device: " + deviceName);
        MutableCapabilities capabilities = new MutableCapabilities();
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("appium:deviceName", deviceName);
        capabilities.setCapability("appium:automationName", "UiAutomator2");
        capabilities.setCapability("appium:app", 
            CommonUtil.getMobileConfigProperty("sauce_android_app_storage"));
        capabilities.setCapability("appium:appActivity", 
            CommonUtil.getMobileConfigProperty("android_app_activity"));
        capabilities.setCapability("appium:appPackage", 
            CommonUtil.getMobileConfigProperty("android_app_package"));
        capabilities.setCapability("newCommandTimeout", "60");
        if (platformVersion != null)
          capabilities.setCapability("appium:platformVersion", platformVersion); 
        if (Boolean.parseBoolean(CommonUtil.getMobileConfigProperty("clear_cache"))) {
          capabilities.setCapability("appium:noReset", false);
        } else {
          capabilities.setCapability("appium:noReset", true);
        } 
        capabilities.setCapability("appium:fullReset\t", false);
        capabilities.setCapability("unicodeKeyboard", true);
        capabilities.setCapability("resetKeyboard", true);
        MutableCapabilities sauceOptions = new MutableCapabilities();
        sauceOptions.setCapability("build", "Android_NativeApp_Tests");
        sauceOptions.setCapability("name", this.actionDriver.getTestCaseName());
        String tunnel_id = CommonUtil.getConfigProperty("tunnel_id");
        if (tunnel_id != null && tunnel_id.length() > 3) {
          this.commonUtil.log("Using tunnel id: " + tunnel_id);
          sauceOptions.setCapability("tunnelIdentifier", tunnel_id);
        } 
        if (appiumVersion != null) {
          sauceOptions.setCapability("appiumVersion", appiumVersion);
        } else if (CommonUtil.getMobileConfigProperty("sauce_appium_version") != null && 
          CommonUtil.getMobileConfigProperty("sauce_appium_version").length() > 2) {
          sauceOptions.setCapability("appiumVersion", 
              CommonUtil.getMobileConfigProperty("sauce_appium_version"));
        } 
        capabilities.setCapability("sauce:options", sauceOptions);
        this.commonUtil.log(capabilities.toString());
        return (Capabilities)capabilities;
      } 
    } else {
      UiAutomator2Options options = new UiAutomator2Options();
      File app = new File(String.valueOf(String.valueOf(System.getProperty("user.dir"))) + 
          CommonUtil.getMobileConfigProperty("local_android_app_path"));
      options.setApp(app.getAbsolutePath());
      options.setAutomationName("UiAutomator2");
      options.setPlatformName("Android");
      options.setNewCommandTimeout(Duration.ofSeconds(60L));
      options.setCapability("unicodeKeyboard", true);
      options.setCapability("resetKeyboard", true);
      options.setDeviceName(CommonUtil.getMobileConfigProperty("local_android_device_id"));
      options.setAppPackage(CommonUtil.getMobileConfigProperty("android_app_package"));
      options.setAppActivity(CommonUtil.getMobileConfigProperty("android_app_activity"));
      if (Boolean.parseBoolean(CommonUtil.getMobileConfigProperty("clear_cache"))) {
        options.setNoReset(false);
      } else {
        options.setNoReset(true);
      } 
      this.commonUtil.log(options.toString());
      return (Capabilities)options;
    } 
    return null;
  }
  
  public Capabilities androidChromeOptions() {
    this.commonUtil.log("Setting capabilities for Android Chrome Browser....");
    if (Boolean.parseBoolean(CommonUtil.getMobileConfigProperty("run_mobile_test_on_saucelab"))) {
      String hubUrl = CommonUtil.getConfigProperty("grid_hub_url");
      String deviceName = null;
      String appiumVersion = null;
      String platformVersion = null;
      if (hubUrl.contains("saucelabs")) {
        if (CommonUtil.getMobileConfigProperty("device_type").equalsIgnoreCase("simulator")) {
          String[] arr = CommonUtil.getMobileConfigProperty("sauce_android_emulator")
            .split("\\|");
          deviceName = arr[0];
          platformVersion = arr[1];
          appiumVersion = arr[2];
        } else {
          deviceName = Device.getSauceAndroidRealDevice();
        } 
        this.commonUtil.log("Hub Url: " + hubUrl);
        this.commonUtil.log("Device: " + deviceName);
        MutableCapabilities capabilities = new MutableCapabilities();
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("browserName", "Chrome");
        capabilities.setCapability("appium:deviceName", deviceName);
        capabilities.setCapability("appium:automationName", "UiAutomator2");
        if (platformVersion != null)
          capabilities.setCapability("appium:platformVersion", platformVersion); 
        capabilities.setCapability("appium:newCommandTimeout", Integer.valueOf(90));
        capabilities.setCapability("appium:autoAcceptAlerts", true);
        capabilities.setCapability("acceptInsecureCerts", true);
        MutableCapabilities sauceOptions = new MutableCapabilities();
        sauceOptions.setCapability("build", "Sauce Android Chrome Build");
        sauceOptions.setCapability("name", this.actionDriver.getTestCaseName());
        if (appiumVersion != null) {
          sauceOptions.setCapability("appiumVersion", appiumVersion);
        } else if (CommonUtil.getMobileConfigProperty("sauce_appium_version") != null && 
          CommonUtil.getMobileConfigProperty("sauce_appium_version").length() > 2) {
          sauceOptions.setCapability("appiumVersion", 
              CommonUtil.getMobileConfigProperty("sauce_appium_version"));
        } 
        String tunnel_id = CommonUtil.getConfigProperty("tunnel_id");
        if (tunnel_id != null && tunnel_id.length() > 3) {
          this.commonUtil.log("Using tunnel id: " + tunnel_id);
          sauceOptions.setCapability("tunnelIdentifier", tunnel_id);
        } 
        capabilities.setCapability("sauce:options", sauceOptions);
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setCapability("unicodeKeyboard", true);
        chromeOptions.setCapability("resetKeyboard", true);
        chromeOptions.addArguments(new String[] { "--ignore-ssl-errors=yes" });
        chromeOptions.addArguments(new String[] { "--ignore-certificate-errors" });
        capabilities.setCapability("goog:chromeOptions", chromeOptions);
        this.commonUtil.log("sauceOptions: " + sauceOptions);
        return (Capabilities)capabilities;
      } 
    } else {
      UiAutomator2Options options = new UiAutomator2Options();
      options.setCapability("newCommandTimeout", Integer.valueOf(80));
      options.setCapability("unicodeKeyboard", true);
      options.setCapability("resetKeyboard", true);
      options.setCapability("platformName", "Android");
      options.setCapability("browserName", Browser.CHROME.toString());
      options.setCapability("automationName", "UiAutomator2");
      options.setCapability("deviceName", 
          CommonUtil.getMobileConfigProperty("local_android_device_id"));
      return (Capabilities)options;
    } 
    return null;
  }
  
  public Capabilities iosSafariOptions() {
    this.commonUtil.log("Setting capabilities for IOS Safari Browser....");
    if (Boolean.parseBoolean(CommonUtil.getMobileConfigProperty("run_mobile_test_on_saucelab"))) {
      String hubUrl = CommonUtil.getConfigProperty("grid_hub_url");
      this.commonUtil.log("Hub Url: " + hubUrl);
      if (hubUrl.contains("saucelabs")) {
        String deviceName = null;
        String platformVersion = null;
        String appiumVersion = null;
        if (CommonUtil.getMobileConfigProperty("device_type").equalsIgnoreCase("simulator")) {
          String[] arr = CommonUtil.getMobileConfigProperty("sauce_ios_simulator")
            .split("\\|");
          deviceName = arr[0];
          platformVersion = arr[1];
          appiumVersion = arr[2];
        } else {
          deviceName = Device.getSauceiOSRealDevice();
        } 
        this.commonUtil.log("deviceName: " + deviceName);
        MutableCapabilities capabilities = new MutableCapabilities();
        capabilities.setCapability("appium:newCommandTimeout", Integer.valueOf(90));
        capabilities.setCapability("appium:autoAcceptAlerts", true);
        capabilities.setCapability("platformName", "iOS");
        capabilities.setCapability("browserName", "Safari");
        capabilities.setCapability("appium:deviceName", deviceName);
        capabilities.setCapability("appium:automationName", "XCUITest");
        if (platformVersion != null)
          capabilities.setCapability("appium:platformVersion", platformVersion); 
        MutableCapabilities sauceOptions = new MutableCapabilities();
        sauceOptions.setCapability("name", this.actionDriver.getTestCaseName());
        if (appiumVersion != null) {
          sauceOptions.setCapability("appiumVersion", appiumVersion);
        } else if (CommonUtil.getMobileConfigProperty("sauce_appium_version") != null && 
          CommonUtil.getMobileConfigProperty("sauce_appium_version").length() > 2) {
          sauceOptions.setCapability("appiumVersion", 
              CommonUtil.getMobileConfigProperty("sauce_appium_version"));
        } 
        String tunnel_id = CommonUtil.getConfigProperty("tunnel_id");
        if (tunnel_id != null && tunnel_id.length() > 3)
          sauceOptions.setCapability("tunnelIdentifier", tunnel_id); 
        capabilities.setCapability("sauce:options", sauceOptions);
        this.commonUtil.log("sauceOptions: " + sauceOptions);
        this.commonUtil.log("capabilities: " + capabilities);
        return (Capabilities)capabilities;
      } 
      Assert.assertTrue(false, "Framework is only supporting Sauce Lab for now...");
    } else {
      XCUITestOptions options = new XCUITestOptions();
      options.setPlatformName("iOS");
      options.setAutomationName("XCUITest");
      options.setNewCommandTimeout(Duration.ofSeconds(90L));
      options.setWdaLaunchTimeout(Duration.ofSeconds(90L));
      options.setDeviceName(CommonUtil.getMobileConfigProperty("local_ios_device_name"));
      if (Boolean.parseBoolean(CommonUtil.getMobileConfigProperty("clear_cache"))) {
        options.setNoReset(false);
      } else {
        options.setNoReset(true);
      } 
      return (Capabilities)options;
    } 
    return null;
  }
  
  public Capabilities iosNativeAppOptions() {
    this.commonUtil.log("Setting capabilities for IOS Native App..");
    String hubUrl = CommonUtil.getConfigProperty("grid_hub_url");
    String deviceName = null;
    String platformVersion = null;
    String appiumVersion = null;
    if (Boolean.parseBoolean(CommonUtil.getMobileConfigProperty("RUN_MOBILE_TEST_ON_SAUCELAB"))) {
      if (hubUrl.contains("saucelabs")) {
        if (Device.getCloudMobileDeviceName().contains("Simulator")) {
        	deviceName=Device.getCloudMobileDeviceName();
        	platformVersion=CommonUtil.getMobileConfigProperty("CLOUD_DEVICES_MOBILE_PLATFORM_VERSION");
        	appiumVersion=CommonUtil.getMobileConfigProperty("CLOUD_APPIUM_VERSION");
        } else {
          Assert.assertTrue(false, "IOS Native app automation on saucelab real device not supported yet...");
        } 
        this.commonUtil.log("Hub Url: " + hubUrl);
        this.commonUtil.log("Device: " + deviceName);
        MutableCapabilities capabilities = new MutableCapabilities();
        capabilities.setCapability("platformName", "iOS");
        capabilities.setCapability("appium:deviceName", deviceName);
        capabilities.setCapability("appium:automationName", "XCUITest");
        String cloudIosAppStorage="storage:filename="+CommonUtil.getMobileConfigProperty("sauce_ios_app_storage");
        capabilities.setCapability("appium:app",cloudIosAppStorage);
        capabilities.setCapability("newCommandTimeout", "60");
        if (platformVersion != null)
          capabilities.setCapability("appium:platformVersion", platformVersion); 
        if (Boolean.parseBoolean(CommonUtil.getMobileConfigProperty("CLEAR_CACHE"))) {
          capabilities.setCapability("appium:noReset", false);
        } else {
          capabilities.setCapability("appium:noReset", true);
        } 
//        capabilities.setCapability("appium:fullReset\t", false);
//        capabilities.setCapability("unicodeKeyboard", true);
//        capabilities.setCapability("resetKeyboard", true);
        capabilities.setCapability("bundleId", "com.saucelabs.mydemoapp.rn");
        MutableCapabilities sauceOptions = new MutableCapabilities();
        sauceOptions.setCapability("build", "IOS_Mydemo_NativeApp_Tests");
        sauceOptions.setCapability("name", this.actionDriver.getTestCaseName());
        String tunnel_id = CommonUtil.getConfigProperty("tunnel_id");
        String accessKey = CommonUtil.getConfigProperty("accessKey");
        if (tunnel_id != null && tunnel_id.length() > 3) {
        	sauceOptions.setCapability("username", tunnel_id);
        	sauceOptions.setCapability("accessKey", accessKey);
         
        } 
        if (appiumVersion != null) {
          sauceOptions.setCapability("appiumVersion", appiumVersion);
        } else if (CommonUtil.getMobileConfigProperty("CLOUD_APPIUM_VERSION") != null && 
          CommonUtil.getMobileConfigProperty("CLOUD_APPIUM_VERSION").length() > 2) {
          sauceOptions.setCapability("appiumVersion", CommonUtil.getMobileConfigProperty("CLOUD_APPIUM_VERSION"));
        } 
        capabilities.setCapability("sauce:options", sauceOptions);
//        this.commonUtil.log(capabilities.toString());
//        this.commonUtil.log(sauceOptions.toString());
        return (Capabilities)capabilities;
      } 
    } else {
      DesiredCapabilities capabilities = new DesiredCapabilities();
      capabilities.setCapability("newCommandTimeout", Integer.valueOf(120));
      capabilities.setCapability("automationName", "XCuiTest");
      capabilities.setCapability("platformName", "iOS");
      capabilities.setCapability("udid", 
          CommonUtil.getMobileConfigProperty("local_ios_device_id"));
      capabilities.setCapability("deviceName", 
          CommonUtil.getMobileConfigProperty("local_ios_device_name"));
      if (Boolean.parseBoolean(CommonUtil.getMobileConfigProperty("clear_cache"))) {
        capabilities.setCapability("noReset", false);
      } else {
        capabilities.setCapability("noReset", true);
      } 
      capabilities.setCapability("fullReset", false);
      if (CommonUtil.getMobileConfigProperty("local_ios_app_path") != null && 
        CommonUtil.getMobileConfigProperty("local_ios_app_path").length() > 2) {
        this.commonUtil.log(
            "IOS APP Path: " + CommonUtil.getMobileConfigProperty("local_ios_app_path"));
        File app = new File(CommonUtil.getMobileConfigProperty("local_ios_app_path"));
        capabilities.setCapability("app", app.getAbsolutePath());
      } 
      this.commonUtil.log("capabilities: " + capabilities);
      return (Capabilities)capabilities;
    } 
    return null;
  }
  
  public Capabilities chromeOptions() {
    this.commonUtil.log("Setting capabilities for DesktOP Chrome Browser....");
    ChromeOptions chromeOptions = new ChromeOptions();
    chromeOptions.setCapability("acceptInsecureCerts", true);
    chromeOptions.addArguments(new String[] { "--remote-allow-origins=*" });
    if (Boolean.parseBoolean(CommonUtil.getConfigProperty("run_on_grid"))) {
      String hubUrl = CommonUtil.getConfigProperty("grid_hub_url");
      this.commonUtil.log("Hub Url: " + hubUrl);
      String tunnel_id = CommonUtil.getConfigProperty("tunnel_id");
      if (hubUrl != null && hubUrl.contains("saucelab") && tunnel_id != null && tunnel_id.length() > 3) {
        this.commonUtil.log("Using tunnel id: " + tunnel_id);
        chromeOptions.setCapability("tunnelIdentifier", tunnel_id);
        chromeOptions.setCapability("name", this.actionDriver.getTestCaseName());
      } 
      return (Capabilities)chromeOptions;
    } 
    String sysChromeProfile = CommonUtil.getConfigProperty("use_system_chrome_profile");
    if (sysChromeProfile != null && Boolean.parseBoolean(sysChromeProfile)) {
//      ExtentReportLogger.logInfo("setting chrome profile to default...");
      this.commonUtil.log("setting chrome profile to default...");
      chromeOptions.addArguments(new String[] { "--user-data-dir=" + CommonUtil.getConfigProperty("chrome_profile_path") });
      chromeOptions.addArguments(new String[] { "--profile-directory=" + CommonUtil.getConfigProperty("chrome_profile_name") });
    } 
    return (Capabilities)chromeOptions;
  }
  
  public Capabilities firefoxOptions() {
    FirefoxOptions firefoxOption = new FirefoxOptions();
    firefoxOption.setAcceptInsecureCerts(true);
    if (Boolean.parseBoolean(CommonUtil.getConfigProperty("run_on_grid"))) {
      String hubUrl = CommonUtil.getConfigProperty("grid_hub_url");
      this.commonUtil.log("Hub Url: " + hubUrl);
      String tunnel_id = CommonUtil.getConfigProperty("tunnel_id");
      if (hubUrl != null && hubUrl.contains("saucelab") && tunnel_id != null && tunnel_id.length() > 3) {
        this.commonUtil.log("Using tunnel id: " + tunnel_id);
        firefoxOption.setCapability("tunnelIdentifier", tunnel_id);
        firefoxOption.setCapability("name", this.actionDriver.getTestCaseName());
      } 
      return (Capabilities)firefoxOption;
    } 
    return (Capabilities)firefoxOption;
  }
  
  public Capabilities safariOptions() {
    this.commonUtil.log("Setting capabilities for Mac Safari Browser....");
    SafariOptions safariOptions = new SafariOptions();
    safariOptions.setCapability("browserVersion", "latest");
    if (Boolean.parseBoolean(CommonUtil.getConfigProperty("run_on_grid"))) {
      String hubUrl = CommonUtil.getConfigProperty("grid_hub_url");
      this.commonUtil.log("Hub Url: " + hubUrl);
      String tunnel_id = CommonUtil.getConfigProperty("tunnel_id");
      if (hubUrl != null && hubUrl.contains("saucelab") && tunnel_id != null && tunnel_id.length() > 3) {
        this.commonUtil.log("Using tunnel id: " + tunnel_id);
        safariOptions.setCapability("name", this.actionDriver.getTestCaseName());
        MutableCapabilities sauceOptions = new MutableCapabilities();
        sauceOptions.setCapability("tunnelIdentifier", tunnel_id);
        safariOptions.setCapability("sauce:options", sauceOptions);
      } 
      this.commonUtil.log("Desired Capabilities: " + safariOptions);
      return (Capabilities)safariOptions;
    } 
    Assert.assertTrue(false, "Framework supports safari on saucelab only..");
    return (Capabilities)safariOptions;
  }
  
  public void initializeChromeDriverServer() {
    String log = "Initializing chrome driver on ";
    if (CommonUtil.isWindows()) {
      this.commonUtil.log(String.valueOf(log) + "Windows");
      WebDriverManager.chromedriver().setup();
    } else if (CommonUtil.isUnix()) {
      this.commonUtil.log(String.valueOf(log) + "LINUX");
      WebDriverManager.chromedriver().operatingSystem(OperatingSystem.LINUX).setup();
    } else {
      this.commonUtil.log(String.valueOf(log) + "MAC");
      WebDriverManager.chromedriver().operatingSystem(OperatingSystem.MAC).setup();
    } 
  }
  
  public void initializeFirefoxDriverServer() {
    String log = "Initializing Firefox driver on ";
    if (CommonUtil.isWindows()) {
      this.commonUtil.log(String.valueOf(log) + "Windows");
      WebDriverManager.firefoxdriver().arch64().setup();
    } else if (CommonUtil.isUnix()) {
      this.commonUtil.log(String.valueOf(log) + "LINUX");
      WebDriverManager.firefoxdriver().operatingSystem(OperatingSystem.LINUX).setup();
    } else {
      this.commonUtil.log(String.valueOf(log) + "MAC");
      WebDriverManager.firefoxdriver().operatingSystem(OperatingSystem.MAC).setup();
    } 
  }
}
