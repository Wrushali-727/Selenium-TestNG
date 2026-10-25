package com.example.qa.tests;

import com.example.qa.base.DriverFactory;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Optional;

/**
 * Base test class: manages the WebDriver lifecycle around each test method.
 * A fresh browser is launched before every test and closed afterward,
 * keeping tests independent.
 */
public abstract class BaseTest {

    protected WebDriver driver;
    protected static final String BASE_URL = "https://www.saucedemo.com/";

    @BeforeMethod(alwaysRun = true)
    @Parameters({"baseUrl"})
    public void setUp(@Optional(BASE_URL) String baseUrl) {
        driver = DriverFactory.createDriver();
        driver.get(baseUrl);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
