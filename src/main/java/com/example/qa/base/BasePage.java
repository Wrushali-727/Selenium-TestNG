package com.example.qa.base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * Base class for every Page Object.
 * <p>
 * The constructor calls {@link PageFactory#initElements(WebDriver, Object)} so that
 * every {@code @FindBy} field in a subclass is initialized as a lazy proxy element.
 * Common wait/interaction helpers live here so the page classes stay declarative.
 */
public abstract class BasePage {

    protected final WebDriver driver;
    protected final WebDriverWait wait;

    protected BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        // Page Factory: wire up all @FindBy-annotated fields on the concrete page.
        PageFactory.initElements(driver, this);
    }

    /** Wait until an element is visible, then click it. */
    protected void click(WebElement element) {
        wait.until(ExpectedConditions.elementToBeClickable(element)).click();
    }

    /** Wait until an element is visible, clear it, then type. */
    protected void type(WebElement element, String text) {
        wait.until(ExpectedConditions.visibilityOf(element));
        element.clear();
        element.sendKeys(text);
    }

    /** Wait for visibility and return the trimmed text of an element. */
    protected String getText(WebElement element) {
        return wait.until(ExpectedConditions.visibilityOf(element)).getText().trim();
    }

    /** True if the element is present and displayed within the wait timeout. */
    protected boolean isDisplayed(WebElement element) {
        try {
            return wait.until(ExpectedConditions.visibilityOf(element)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}
