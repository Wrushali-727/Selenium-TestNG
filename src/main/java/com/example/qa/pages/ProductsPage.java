package com.example.qa.pages;

import com.example.qa.base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Page Object for the SauceDemo products (inventory) page shown after a successful login.
 */
public class ProductsPage extends BasePage {

    @FindBy(className = "title")
    private WebElement pageTitle;

    @FindBy(className = "inventory_item")
    private List<WebElement> inventoryItems;

    @FindBy(css = ".inventory_item_name")
    private List<WebElement> productNames;

    @FindBy(id = "react-burger-menu-btn")
    private WebElement menuButton;

    @FindBy(id = "shopping_cart_container")
    private WebElement cartIcon;

    public ProductsPage(WebDriver driver) {
        super(driver);
    }

    /** The page header text, e.g. "Products". */
    public String getTitle() {
        return getText(pageTitle);
    }

    public boolean isLoaded() {
        return isDisplayed(pageTitle) && "Products".equals(getTitle());
    }

    public int getProductCount() {
        return inventoryItems.size();
    }

    public List<String> getProductNames() {
        return productNames.stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }
}
