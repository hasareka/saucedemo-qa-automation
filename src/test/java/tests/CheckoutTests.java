package tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.*;

public class CheckoutTests extends BaseTest {

    @BeforeMethod
    public void loginToInventory() {
        driver.get("https://www.saucedemo.com/");
        LoginPage loginPage = new LoginPage(driver);
        loginPage.enterUsername("standard_user");
        loginPage.enterPassword("secret_sauce");
        loginPage.clickLogin();
        Assert.assertTrue(loginPage.isOnInventoryPage(), "Login failed in setup");
    }

    @Test
    public void checkout_shouldCompleteSuccessfully() {
        InventoryPage inventoryPage = new InventoryPage(driver);
        inventoryPage.addToCart("Sauce Labs Backpack");

        CartPage cartPage = inventoryPage.goToCart();
        CheckoutInfoPage infoPage = cartPage.checkout();
        Assert.assertTrue(infoPage.isLoaded(), "Checkout info page not loaded");

        infoPage.fillInfo("Test", "User", "12345");
        CheckoutOverviewPage overviewPage = infoPage.clickContinue();
        Assert.assertTrue(overviewPage.isLoaded(), "Overview page not loaded");

        CheckoutCompletePage completePage = overviewPage.finish();
        Assert.assertTrue(completePage.isLoaded(), "Complete page not loaded");
        Assert.assertTrue(completePage.getHeaderText().toLowerCase().contains("thank you"),
                "Thank you message not shown");
    }

    @Test
    public void checkout_shouldShowErrorWhenFirstNameMissing() {
        InventoryPage inventoryPage = new InventoryPage(driver);
        inventoryPage.addToCart("Sauce Labs Backpack");

        CartPage cartPage = inventoryPage.goToCart();
        CheckoutInfoPage infoPage = cartPage.checkout();

        infoPage.fillInfo("", "User", "12345");
        infoPage.clickContinue();

        Assert.assertTrue(infoPage.getErrorMessage().toLowerCase().contains("first name"),
                "First name validation error not shown");
    }
}
