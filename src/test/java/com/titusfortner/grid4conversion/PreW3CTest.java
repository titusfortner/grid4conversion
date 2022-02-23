package com.titusfortner.grid4conversion;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

// When these tests are run with Java bindings 2.45:

// Local Chromedriver — All Pass
// Remote Grid 2.53.1 - 3.5 — All Pass
// Remote Grid 3.6+ — All Fail

// Between Selenium 2.46 and 2.53.1 many updates were made to accommodate w3c syntax in Java bindings
// Many of these fail in Selenium 2.53 in other bindings because they were not updated like Java was
// Protocol Converter implemented in 3.7 and never addressed these actual JWP command syntax

public class PreW3CTest {
    private RemoteWebDriver driver;

    @BeforeEach
    void startDriver() throws MalformedURLException {
        driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), DesiredCapabilities.chrome());
    }

    // ID & name have both values returned

    @Test
    void useCSSSelectorForExistingElement() {
        driver.get("https://www.selenium.dev");

        // "Returned value cannot be converted to WebElement"
        Assertions.assertDoesNotThrow(() -> driver.findElement(By.cssSelector(".td-main")));
    }

    @Test
    void useXPathForExistingElement() {
        driver.get("https://www.selenium.dev");

        // "Returned value cannot be converted to WebElement"
        Assertions.assertDoesNotThrow(() -> driver.findElement(By.xpath(".//main")));
    }

    @Test
    void useTagNameForExistingElement() {
        driver.get("https://www.selenium.dev");

        // "Returned value cannot be converted to WebElement"
        Assertions.assertDoesNotThrow(() -> driver.findElement(By.tagName("main")));
    }

    @Test
    void useLinkTextForExistingElement() {
        driver.get("https://www.selenium.dev");

        // "Returned value cannot be converted to WebElement"
        Assertions.assertDoesNotThrow(() -> driver.findElement(By.linkText("Downloads")));
    }

    @Test
    void usePartialLinkTextForExistingElement() {
        driver.get("https://www.selenium.dev");

        // "Returned value cannot be converted to WebElement"
        Assertions.assertDoesNotThrow(() -> driver.findElement(By.partialLinkText("Down")));
    }

    // Fixed in 2.47.2
    @Test
    void scriptTimeout() {
        Assertions.assertDoesNotThrow(() -> {
            driver.manage().timeouts().setScriptTimeout(5, TimeUnit.SECONDS);
        });
    }

    @Test
    void implicitWaitTimeout() {
        Assertions.assertDoesNotThrow(() -> {
            driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        });
    }

    // Fixed in 2.46.0
    @Test
    void switchToIFrameByName() {
        driver.get("http://watir.com/examples/nested_iframes.html");

        Assertions.assertDoesNotThrow(() -> {
            driver.switchTo().frame("one");
        });
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
}
