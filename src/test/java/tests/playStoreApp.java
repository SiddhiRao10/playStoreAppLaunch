package tests;

import commonClass.BaseTest;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.Activity;
import io.appium.java_client.android.AndroidDriver;

import org.openqa.selenium.NotFoundException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import java.time.Duration;

public class playStoreApp extends BaseTest {
    @Test
    public void demoSiteTest() throws InterruptedException {

        System.out.println("android session launched");

        String app = "whatsapp";
        
        // Launch the Play store 
        FluentWait<AppiumDriver<MobileElement>> wait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(20))
                .ignoring(NotFoundException.class);

        try {

            Activity androidActivity = new Activity("com.android.vending",
                    "com.google.android.finsky.activities.MainActivity");
            ((AndroidDriver<MobileElement>) driver).startActivity(androidActivity);
        } catch (Exception e) {
            System.out.println(e);
        }
        
        // Click the Search Box
        MobileElement searchBox = (MobileElement) new WebDriverWait(
                driver,
                30)
                .until(
                        ExpectedConditions.visibilityOfElementLocated(
                                MobileBy.xpath(
                                        "//android.view.View[@content-desc='Search Google Play']")));
        searchBox.click();

        // Type 'whatsapp' in the search bar and enter
        MobileElement searchBar = (MobileElement) new WebDriverWait(
                driver,
                60)
                .until(
                        ExpectedConditions.visibilityOfElementLocated(
                                MobileBy.xpath("//android.widget.EditText[@text='Search for apps & games']")));
        searchBar.sendKeys(app+"\\n");
        
        // Click on the first result : Whatsapp Messenger
        MobileElement resultText = (MobileElement) new WebDriverWait(
                driver,
                30)
                .until(
                        ExpectedConditions.visibilityOfElementLocated(
                                MobileBy.xpath("//android.widget.TextView[@text='WhatsApp Messenger']")));
        resultText.click();

        // Click the Install Button
        MobileElement installButton = (MobileElement) new WebDriverWait(
                driver,
                30)
                .until(
                        ExpectedConditions.visibilityOfElementLocated(
                                MobileBy.xpath("//android.view.View[@content-desc='Install']")));
        installButton.click();

        // Wait for the Open button's parent to be Enabled and Visible - this should indicate that the app is installed
        MobileElement openButton = (MobileElement) new WebDriverWait(
                driver,
                600)
                .until(
                        ExpectedConditions.elementToBeClickable(
                                MobileBy.xpath("//*[android.view.View[@content-desc='Open']]")));
        openButton.click();

        // Voila
        System.out.println("App is installed");

    }
}
