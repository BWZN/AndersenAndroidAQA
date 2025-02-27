package android_package;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class AndroidPage {
    AppiumDriver driver;
    WebDriverWait waiters;

    public AndroidPage(AppiumDriver driver) {
        this.driver = driver;
        waiters = new WebDriverWait(driver, 10);
    }


    public void listCounter() {
        MobileElement element = (MobileElement) driver.findElement(MobileBy.AndroidUIAutomator(
                "new UiScrollable(new UiSelector().scrollable(true)).scrollIntoView(new UiSelector().text(\"Views\"))"));
        element.click();

        List<String> elements = new ArrayList<>();

        boolean scrollable = true;
        int previousSize = 0;

        while (scrollable) {
            List<MobileElement> items = driver.findElements(MobileBy.className("android.widget.TextView"));

            for (MobileElement item : items) {
                String text = item.getText().trim();

                if (!text.isEmpty() && !elements.contains(text) && !text.equals("API Demos")) {
                    elements.add(text);
                }
            }

            if (elements.size() == previousSize) {
                break;
            }

            previousSize = elements.size();
            scrollable = scrollDown();
        }
        Assert.assertEquals(elements.size(), 42);
    }

    private boolean scrollDown() {
        try {
            driver.findElement(MobileBy.AndroidUIAutomator(
                    "new UiScrollable(new UiSelector().scrollable(true)).scrollForward()"));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void dataChanger() {
        driver.findElement(MobileBy.AccessibilityId("Views")).click();

        MobileElement element2 = (MobileElement) driver.findElement(MobileBy.AndroidUIAutomator(
                "new UiScrollable(new UiSelector().scrollable(true)).scrollIntoView(new UiSelector().text(\"Date Widgets\"))"));
        element2.click();

        driver.findElement(MobileBy.AccessibilityId("1. Dialog")).click();

        setDay();

        setTime();
    }

    public void setDay() {
        driver.findElement(MobileBy.AccessibilityId("change the date")).click();

        LocalDate nextDay = LocalDate.now().plusDays(1);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMMM yyyy", Locale.ENGLISH);
        String formattedDate = nextDay.format(formatter);

        System.out.println(formattedDate);

        String dateSelector = "new UiSelector().descriptionContains(\"" + formattedDate + "\")";
        driver.findElement(MobileBy.AndroidUIAutomator("new UiScrollable(new UiSelector().scrollable(true)).scrollIntoView(" + dateSelector + ")")).click();

        driver.findElement(MobileBy.id("android:id/button1")).click();
    }

    public void setTime() {
        driver.findElement(MobileBy.AccessibilityId("change the time (spinner)")).click();

        MobileElement hours = (MobileElement) driver.findElement(MobileBy.xpath("//android.widget.EditText[@resource-id='android:id/numberpicker_input' and @text='1']"));
        hours.clear();
        hours.sendKeys("11");

        MobileElement minutes = (MobileElement) driver.findElement(MobileBy.xpath("//android.widget.EditText[@resource-id='android:id/numberpicker_input' and @text='02']"));
        minutes.clear();
        minutes.sendKeys("11");

        driver.findElement(MobileBy.AndroidUIAutomator(
                "new UiScrollable(new UiSelector().scrollable(true)).scrollIntoView(new UiSelector().text(\"PM\"))"
        )).click();

        driver.findElementById("android:id/button1").click();
    }


    public void buttonCheck() {
        driver.findElement(MobileBy.AccessibilityId("Views")).click();

        MobileElement element2 = (MobileElement) driver.findElement(MobileBy.AndroidUIAutomator(
                "new UiScrollable(new UiSelector().scrollable(true)).scrollIntoView(new UiSelector().text(\"TextSwitcher\"))"));
        element2.click();

        MobileElement nextButton = (MobileElement) driver.findElement(MobileBy.AccessibilityId("Next"));
        MobileBy.AndroidUIAutomator("new UiSelector().text(\"0\")");
        MobileElement textElement;

        int clickCount = 10;

        for (int i = 1; i <= clickCount; i++) {
            nextButton.click();

            textElement = (MobileElement) driver.findElement(MobileBy.AndroidUIAutomator("new UiSelector().text(\"" + i + "\")"));
            String displayedText = textElement.getText();

            Assert.assertEquals(displayedText, String.valueOf(i));
        }
    }
}

