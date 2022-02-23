package com.titusfortner.grid4conversion;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.RemoteWebElement;

import java.net.MalformedURLException;
import java.net.URL;

// When these tests are run with Java bindings 2.53.1:

// Remote Server 2.53.1 - 3.5 — All Pass
// Remote Server 3.6 - 3.141.59 — All Fail
// Fixed in 4.0

public class Grid3Test {
    private RemoteWebDriver driver;

    @BeforeEach
    void startDriver() throws MalformedURLException {
        driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), DesiredCapabilities.chrome());
    }

    @Test
    void rightClick() {
        driver.get("http://watir.com/examples/right_click.html");
        RemoteWebElement div = (RemoteWebElement) driver.findElement(By.id("click"));

        // mouseMoveTo is broken in 3.7 for some other reason
        Assertions.assertDoesNotThrow(() -> driver.getMouse().contextClick(div.getCoordinates()));

        Assertions.assertDoesNotThrow(() -> driver.findElement(By.cssSelector("#messages div")));
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
}
