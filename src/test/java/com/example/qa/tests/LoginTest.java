package com.example.qa.tests;

import com.example.qa.pages.LoginPage;
import com.example.qa.pages.ProductsPage;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * TestNG tests for the SauceDemo login flow, driven through Page Objects.
 */
public class LoginTest extends BaseTest {

    @Test(description = "Valid credentials land the user on the Products page")
    public void validLoginShowsProductsPage() {
        LoginPage loginPage = new LoginPage(driver);
        ProductsPage productsPage = loginPage.loginAs("standard_user", "secret_sauce");

        Assert.assertTrue(productsPage.isLoaded(), "Products page should load after valid login");
        Assert.assertEquals(productsPage.getTitle(), "Products");
        Assert.assertEquals(productsPage.getProductCount(), 6, "SauceDemo lists 6 products");
    }

    @Test(description = "A locked-out user sees an error and stays on the login page")
    public void lockedOutUserSeesError() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.loginExpectingFailure("locked_out_user", "secret_sauce");

        Assert.assertTrue(loginPage.isErrorDisplayed(), "An error message should be shown");
        Assert.assertTrue(
                loginPage.getErrorMessage().contains("locked out"),
                "Error should mention the user is locked out");
    }

    @Test(dataProvider = "invalidCredentials",
          description = "Invalid credentials are rejected with an error")
    public void invalidLoginIsRejected(String username, String password, String expectedFragment) {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.loginExpectingFailure(username, password);

        Assert.assertTrue(loginPage.isErrorDisplayed(), "An error message should be shown");
        Assert.assertTrue(
                loginPage.getErrorMessage().contains(expectedFragment),
                "Unexpected error message: " + loginPage.getErrorMessage());
    }

    @DataProvider(name = "invalidCredentials")
    public Object[][] invalidCredentials() {
        return new Object[][]{
                {"standard_user", "wrong_password", "Username and password do not match"},
                {"", "secret_sauce", "Username is required"},
                {"standard_user", "", "Password is required"},
        };
    }
}
