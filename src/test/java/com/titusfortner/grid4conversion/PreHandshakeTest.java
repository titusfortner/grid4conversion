package com.titusfortner.grid4conversion;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Set;

// When these tests are run with Java bindings 2.53.1:

// Remote Server 2.53.1 - 3.5 — All Pass
// Remote Server 3.6+ — All Fail

public class PreHandshakeTest {
    private RemoteWebDriver driver;

    @BeforeEach
    void startDriver() throws MalformedURLException {
        driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), DesiredCapabilities.chrome());
    }

    @Test
    void switchWindows() {
        Set<String> windowHandles = driver.getWindowHandles();
        String handle = windowHandles.stream().findFirst().get();

        Assertions.assertDoesNotThrow(() -> driver.switchTo().window(handle));
    }

    @Test
    void sendKeysToActiveElement() {
        driver.get("http://watir.com/examples/forms_with_input_elements.html");

        // org.openqa.selenium.UnsupportedCommandException: sendKeysToActiveElement
        Assertions.assertDoesNotThrow(() -> driver.getKeyboard().sendKeys("keys"));
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
}
