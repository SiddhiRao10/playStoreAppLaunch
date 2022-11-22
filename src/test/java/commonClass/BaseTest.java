package commonClass;

import com.browserstack.local.Local;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import utils.MobileHelper;

import java.io.File;
import java.io.FileReader;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class BaseTest {

    static Local bsLocal = new Local();
    DesiredCapabilities capabilities = new DesiredCapabilities();
    String username = System.getenv("BROWSERSTACK_USERNAME");
    public String accessKey = System.getenv("BROWSERSTACK_ACCESS_KEY");
    String buildName = System.getenv("BROWSERSTACK_BUILD_NAME");
    String googleUserName = System.getenv("GOOGLE_USERNAME");
    String googlePassword = System.getenv("GOOGLE_PASSWORD");
    String appID = System.getenv("BROWSERSTACK_APP_ID");
    private static final String BROWSERSTACK_HUB_URL = "hub-cloud.browserstack.com";
    public MobileHelper mobileHelper;

    public  AppiumDriver<MobileElement> driver;
    @BeforeMethod(alwaysRun = true)
    @Parameters(value = {"config", "environment"})
    public void setUp(String config_file, String environment) throws Exception {
        File name = new File("src/test/commmon/demo.playstore.json");
            JSONParser parser = new JSONParser();
            JSONObject config = (JSONObject) parser.parse(new FileReader("src/test/common/" + config_file));
            JSONObject envs = (JSONObject) config.get("environments");
            Map<String, String> envCapabilities = (Map<String, String>) envs.get(environment);
            Iterator it = envCapabilities.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry pair = (Map.Entry) it.next();
                capabilities.setCapability(pair.getKey().toString(), pair.getValue().toString());
            }
            Map<String, String> commonCapabilities = (Map<String, String>) config.get("capabilities");
            it = commonCapabilities.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry pair = (Map.Entry) it.next();
                if (capabilities.getCapability(pair.getKey().toString()) == null) {
                    capabilities.setCapability(pair.getKey().toString(), pair.getValue().toString());
                    capabilities.setCapability("build", buildName);
                    capabilities.setCapability("app", appID);
                    capabilities.setCapability("browserstack.networkLogs", "true");
                    capabilities.setCapability("browserstack.user",username);
                    capabilities.setCapability("browserstack.key",accessKey);
                    capabilities.setCapability("browserstack.appStoreConfiguration", new HashMap<String, String>(){{put("username", googleUserName);put("password", googlePassword);}});

                }
            }
            driver = new AndroidDriver<MobileElement>(new URL("https://" + username + ":" + accessKey + "@" + BROWSERSTACK_HUB_URL + "/wd/hub"), capabilities);


    }
    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result) throws Exception {
        JavascriptExecutor jse = (JavascriptExecutor)driver;
        if (result.getStatus() == ITestResult.SUCCESS) {
            jse.executeScript("browserstack_executor: {\"action\": \"setSessionStatus\", \"arguments\": {\"status\": \"passed\", \"reason\": \"Test Passed\"}}");
        }
        else
            jse.executeScript("browserstack_executor: {\"action\": \"setSessionStatus\", \"arguments\": {\"status\": \"failed\", \"reason\": \"Test Failed\"}}");

        driver.quit();
    }
}
