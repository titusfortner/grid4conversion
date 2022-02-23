package com.titusfortner.grid4conversion;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

// These are working in Selenium Server 2 & 3
// These were broken when trying to do the Locator Conversions in Grid 4

public class PreLocatorConversionTest {
    private RemoteWebDriver driver;

    @BeforeEach
    void startDriver() throws MalformedURLException {
        driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), DesiredCapabilities.chrome());
    }

    @Test
    void useIDForMissingElement() {
        NoSuchElementException thrown = Assertions.assertThrows(NoSuchElementException.class, () -> {
            driver.findElement(By.id("not-there"));
        });

        String msg = "no such element: Unable to locate element";
        Assertions.assertTrue(thrown.getMessage().contains(msg));
    }

    @Test
    void useNameForMissingElement() {
        NoSuchElementException thrown = Assertions.assertThrows(NoSuchElementException.class, () -> {
            driver.findElement(By.name("not-there"));
        });

        String msg = "no such element: Unable to locate element";
        Assertions.assertTrue(thrown.getMessage().contains(msg));
    }

    @Test
    void useClassNameForExistingElement() {
        driver.get("https://www.selenium.dev");

        // "invalid argument, message=Unable to determine element locating strategy for class name"
        Assertions.assertDoesNotThrow(() -> driver.findElement(By.className("td-main")));
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
}
