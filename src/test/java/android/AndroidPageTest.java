package android;

import android_package.AndroidPage;
import android_package.AppiumDriverInit;
import io.appium.java_client.AppiumDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class AndroidPageTest {
    AppiumDriver driver;
    AndroidPage androidPage;

    @BeforeMethod
    public void setUp(){
        driver =  new AppiumDriverInit().getDriver();
        androidPage = new AndroidPage(driver);
    }

    @AfterMethod
    public void close(){
        driver.quit();
    }

    @Test
    public void countItems(){
        androidPage.listCounter();
    }

    @Test
    public void dataChanger(){
        androidPage.dataChanger();
    }

    @Test
    public void buttonCheck(){
        androidPage.buttonCheck();
    }
}
